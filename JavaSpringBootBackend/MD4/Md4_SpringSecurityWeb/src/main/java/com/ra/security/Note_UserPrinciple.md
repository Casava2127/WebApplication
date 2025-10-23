
---

## 🧩 Bản chất của `UserPrinciple`

`UserPrinciple` **không phải là bản sao dữ liệu trong database**,
mà là **một lớp đại diện (adapter)** giúp Spring Security **hiểu và làm việc được** với thực thể `User` của bạn.

---

### 🔹 Cụ thể:

* Trong **database**, bạn có entity `User` — chứa dữ liệu người dùng thực tế (username, password, roles, status...).
* Nhưng **Spring Security** không biết gì về class `User` của bạn cả.
  Nó chỉ hiểu interface `UserDetails`.

👉 Vì vậy bạn tạo `UserPrinciple` để:

* “**bọc**” (wrap) đối tượng `User`
* và **chuyển đổi nó** sang dạng mà Spring Security hiểu (`UserDetails`).

---

## 🧠 Hiểu bằng ví dụ trực quan:

| Thành phần              | Vai trò                                    | Giải thích                                                                          |
| ----------------------- | ------------------------------------------ | ----------------------------------------------------------------------------------- |
| `User`                  | Dữ liệu người dùng trong database          | Chứa thông tin thật (username, password, roles, status, v.v.)                       |
| `UserPrinciple`         | Lớp đại diện (adapter) cho Spring Security | “Phiên bản bảo mật hóa” của `User`, dùng trong quá trình login, xác thực            |
| `SecurityContextHolder` | Bộ nhớ tạm của Spring Security             | Sau khi xác thực xong, Spring lưu `UserPrinciple` ở đây để biết “ai đang đăng nhập” |

---

## 🔁 Luồng hoạt động minh họa

```
[Client gửi request login]
       ↓
UserDetailsService (loadUserByUsername)`
       ↓
Tìm User trong DB (bảng users)
       ↓
Tạo UserPrinciple(user, authorities)
       ↓
AuthenticationManager xác thực
       ↓
Nếu hợp lệ → lưu UserPrinciple vào SecurityContextHolder
       ↓
Từ đây về sau, mọi request đều biết "người dùng hiện tại" thông qua UserPrinciple
```

---

## ⚙️ Ví dụ đơn giản:

```java
User user = userRepository.findByUsername("admin").get();
UserPrinciple userPrinciple = new UserPrinciple(user, roles);
```

→ `userPrinciple` là “phiên bản bảo mật” của `user` dùng nội bộ trong Spring Security.

---

✅ **Tóm lại:**

* `User` → dữ liệu trong DB.
* `UserPrinciple` → lớp “chuyển đổi” để Spring Security làm việc với `User`.
* `UserPrinciple` **không ghi vào DB**, mà chỉ tồn tại trong quá trình xác thực.
* Sau khi login thành công, `UserPrinciple` được lưu trong `SecurityContextHolder` để Spring biết ai đang đăng nhập, có quyền gì.

---
Đúng rồi ✅ — bạn đang hiểu **rất đúng bản chất** của cách Spring Security hoạt động khi dùng **JWT + DaoAuthenticationProvider**.
Giờ mình sẽ diễn giải kỹ hơn **từng bước** theo luồng **bản chất nội tại (core flow)** nhé 👇

---

### 🧩 **1. Vai trò của `UserPrinciple`**

* `UserPrinciple` là **lớp ánh xạ (adapter)** giữa **thực thể `User` trong database** và **cơ chế bảo mật của Spring Security**.
* `Spring Security` không làm việc trực tiếp với `User` trong DB, mà làm việc với interface `UserDetails`.
* Do đó, `UserPrinciple implements UserDetails` giúp **chuyển đổi dữ liệu từ DB sang dạng mà Spring Security hiểu được**.

➡️ Nó chứa:

* `username`, `password`
* `authorities` (quyền, vai trò)
* `status` (trạng thái kích hoạt tài khoản)

=> Khi Spring Security xác thực thành công, `UserPrinciple` chính là **đối tượng người dùng hiện tại** được lưu trong **`SecurityContextHolder`** suốt phiên đó.

---

### ⚙️ **2. Luồng xác thực chi tiết (Authentication Flow)**

#### 🧾 Bước 1: Người dùng gửi yêu cầu đăng nhập

* Gửi `POST /login` kèm `username`, `password`.

#### 🔍 Bước 2: Tạo `UsernamePasswordAuthenticationToken`

* Spring Security tự động tạo đối tượng:

  ```java
  UsernamePasswordAuthenticationToken(username, password)
  ```
* Đối tượng này **chưa được xác thực** (`isAuthenticated = false`).

#### ⚙️ Bước 3: `AuthenticationManager` → `DaoAuthenticationProvider`

* `AuthenticationManager` chuyển yêu cầu tới `DaoAuthenticationProvider` (provider mặc định của Spring).

#### 🧩 Bước 4: `DaoAuthenticationProvider` gọi `UserDetailsService`

* `DaoAuthenticationProvider` gọi:

  ```java
  userDetailsService.loadUserByUsername(username)
  ```
* Hàm này trong code của bạn **truy vấn DB**, lấy `User` và chuyển sang `UserPrinciple`.

  ```java
  return new UserPrinciple(user, authorities);
  ```

#### 🔑 Bước 5: So sánh mật khẩu

* `DaoAuthenticationProvider` dùng `PasswordEncoder.matches(rawPassword, encodedPassword)`
* Nếu khớp → xác thực thành công.

#### ✅ Bước 6: Tạo đối tượng `Authentication` đã xác thực

* Lúc này `AuthenticationManager` tạo ra một `UsernamePasswordAuthenticationToken` mới:

  ```java
  UsernamePasswordAuthenticationToken(userPrinciple, null, authorities)
  ```
* Với `isAuthenticated = true`.

#### 🧠 Bước 7: Lưu vào `SecurityContextHolder`

* Spring lưu `Authentication` này trong:

  ```java
  SecurityContextHolder.getContext().setAuthentication(authentication)
  ```
* Tức là **mỗi request sau đó đều biết “ai đang đăng nhập”**.

#### 🪙 Bước 8: Tạo JWT Token

* Sau khi xác thực thành công, `JwtProvider` dùng `UserPrinciple` để:

  ```java
  generateToken(userPrinciple)
  ```
* Token được trả về client.

---

### 🔁 **9. Các request sau (đã có Token)**

* Client gửi token qua header `Authorization: Bearer <jwt>`.
* `JwtAuthTokenFilter`:

  * Parse token
  * Validate token
  * Lấy username từ token
  * Gọi lại `UserDetailsService` để load `UserPrinciple`
  * Gắn `Authentication` đã xác thực vào `SecurityContextHolder`

➡️ Nhờ vậy, mỗi request đều **được “gắn danh tính” người dùng hiện tại** mà không cần đăng nhập lại.

---

### 🧠 Tóm tắt bản chất

| Thành phần                  | Vai trò                                               |
| --------------------------- | ----------------------------------------------------- |
| `User`                      | Entity trong DB                                       |
| `UserPrinciple`             | “Bản sao” của User – Spring Security dùng để xác thực |
| `UserDetailsService`        | Truy xuất và chuyển đổi User → UserPrinciple          |
| `DaoAuthenticationProvider` | So sánh mật khẩu, xác thực người dùng                 |
| `AuthenticationManager`     | Điều phối xác thực                                    |
| `JwtProvider`               | Tạo và kiểm tra token                                 |
| `SecurityContextHolder`     | Lưu phiên làm việc đã xác thực                        |

---

Nếu bạn muốn, mình có thể **vẽ UML sequence flow chi tiết 2 pha**:

1. Đăng nhập (Login → tạo token)
2. Gọi API (Authorization → xác thực từ token)

Bạn có muốn mình vẽ luôn không?
