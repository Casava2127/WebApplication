https://aithietke.com/spring-security-spring-boot-security-jwt-architecture/
Câu hỏi của bạn **rất hay và đúng trọng tâm**, mình sẽ giải thích thật kỹ để bạn hiểu **bản chất của sơ đồ vừa rồi** nhé 👇

---

## 🎯 **Câu trả lời ngắn gọn:**

> 👉 Sơ đồ bạn vừa xem **là kiến trúc của Spring Security cho RESTful API**
> sử dụng **cơ chế xác thực & phân quyền dựa trên JWT (JSON Web Token)** —
> tức là **Spring Security API (JWT Authentication)**, **không phải Web Security (Session-based)**.

---

## 🧩 **Phân tích chi tiết**

### 🔹 1️⃣ Loại hệ thống:

* Đây là **Spring Security cho API** (thường là các ứng dụng backend cung cấp dữ liệu JSON, không có session).
* Dùng cho:

  * Mobile App,
  * React / Angular SPA,
  * REST API microservices.

> ⚠️ Không phải Spring Security Web, vì:
>
> * Không dùng `HttpSession`
> * Không có `JSESSIONID`
> * Không có `SecurityContextPersistenceFilter` lưu session
> * Không redirect qua login page, mà **trả JSON token**

---

### 🔹 2️⃣ Cơ chế xác thực (Authentication)

* Dựa trên **JWT Token** thay vì **Session Cookie**.
* Khi user đăng nhập, Spring Security:

  1. Nhận username/password qua `/login`
  2. Gọi `AuthenticationManager → DaoAuthenticationProvider` để kiểm tra DB
  3. Nếu đúng → `JwtProvider` sinh JWT
  4. Trả token về client

> Client lưu token này trong localStorage hoặc header
> và dùng nó để chứng minh danh tính trong các request tiếp theo.

---

### 🔹 3️⃣ Cơ chế phân quyền (Authorization)

* Dựa trên **GrantedAuthority** được nhúng vào token.
* Khi user gọi API:

  * `JwtAuthTokenFilter` lấy token từ header
  * `JwtProvider` parse & xác thực token
  * Tạo lại `Authentication` có các `authorities` (role)
  * Lưu vào `SecurityContextHolder`
  * Khi controller gặp `@PreAuthorize("hasRole('ADMIN')")`, Spring sẽ kiểm tra quyền trong `Authentication`.

---

### 🔹 4️⃣ Không có session → Stateless

* Mỗi request **tự mang danh tính (JWT)** → server không lưu session.
* Cấu hình Spring Boot thường có dòng:

  ```java
  http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  ```
* Do đó, hệ thống **hoàn toàn stateless**, rất phù hợp cho RESTful API.

---

### 🔹 5️⃣ Tóm tắt so sánh: Web Security vs API Security (JWT)

| Đặc điểm           | Spring Security Web               | Spring Security API (JWT)       |
| ------------------ | --------------------------------- | ------------------------------- |
| Cơ chế xác thực    | Username & Password → lưu Session | Username & Password → sinh JWT  |
| Token              | Session ID (JSESSIONID)           | JWT (JSON Web Token)            |
| Lưu trữ trạng thái | Có session trên server            | Stateless (client tự lưu token) |
| Lưu thông tin user | HttpSession                       | SecurityContextHolder (từ JWT)  |
| Giao tiếp          | Browser tự gửi cookie             | Client gửi Authorization Header |
| Kiểu phản hồi      | HTML (redirect login page)        | JSON (token, message)           |
| Dùng cho           | Web app truyền thống              | REST API, SPA, Mobile           |

---

## 📘 Kết luận

✅ Sơ đồ bạn đang có là:

> **Spring Security API (JWT Authentication & Authorization)**
> ➤ Dùng cho hệ thống RESTful (không session, không cookie).
> ➤ Có 2 cơ chế trong đó:
>
> * **Authentication**: Kiểm tra danh tính bằng username/password → sinh JWT.
> * **Authorization**: Kiểm tra quyền truy cập từ JWT token (role, authority).

---


## 🧭 **Tổng quan sơ đồ**

👉 Hình mô tả **cách một HTTP request được Spring Security xử lý** để:

* **Xác thực (Authentication)** người dùng thông qua JWT
* **Ủy quyền (Authorization)** khi người dùng truy cập API được bảo vệ

---

## 🧩 **1. HTTP Request**

* Client (browser, React, mobile app) gửi request tới server (Spring Boot API).
* Có thể là:

    * **Login request:** `/api/auth/login` — gửi username + password.
    * **API request:** `/api/user/info` — gửi kèm `Authorization: Bearer <JWT>` trong header.

---

## 🧰 **2. JwtAuthTokenFilter (extends OncePerRequestFilter)**

* Là **filter đầu tiên** intercept (chặn) mọi request đi vào hệ thống.
* Nhiệm vụ chính:

    * Lấy JWT token từ **header**.
    * Gọi **JwtProvider** để **validate / parse token**.
    * Nếu token hợp lệ → tạo **UsernamePasswordAuthenticationToken** → lưu vào **SecurityContextHolder**.
    * Nếu token không hợp lệ → ném lỗi `AuthenticationException` để **AuthenticationEntryPoint** xử lý.

➡️ **Lưu ý:** Filter này chạy **một lần duy nhất cho mỗi request** nhờ `OncePerRequestFilter`.

---

## 🔑 **3. JwtProvider (hoặc JwtUtils)**

* Là lớp **xử lý JWT**:

    * `generateToken(UserDetails)` → tạo token khi đăng nhập thành công.
    * `validateToken(token)` → kiểm tra chữ ký, hạn token, cấu trúc.
    * `getUsernameFromToken(token)` → trích xuất thông tin người dùng.

📘 Token được ký bằng **secret key** (HS256) hoặc **RSA private key** (RS256).

---

## 👤 **4. AuthenticationManager & DaoAuthenticationProvider**

* **AuthenticationManager** là trung tâm quản lý xác thực (authentication flow).
* Nó **giao nhiệm vụ** cho **DaoAuthenticationProvider**, lớp dùng để:

    1. Gọi `UserDetailsService.loadUserByUsername(username)` để lấy thông tin user từ DB.
    2. Dùng `PasswordEncoder` để so sánh mật khẩu plaintext và hash trong DB.

Nếu hợp lệ → tạo `Authentication` (đã xác thực).
Nếu không → ném `AuthenticationException`.

---

## 🧑‍💻 **5. UserDetailsService & UserDetails (UserPrincipal)**

* **UserDetailsService** là interface của Spring Security, dùng để **tải dữ liệu người dùng** từ cơ sở dữ liệu.
* Khi login, provider gọi:

  ```java
  UserDetails loadUserByUsername(String username)
  ```
* **UserDetails** là model chứa thông tin của user: username, password, roles.
* Bạn thường có class `UserPrincipal` implements `UserDetails` để map từ entity `User` trong DB sang cấu trúc Spring Security.

📊 **MySQL** là nơi chứa bảng `users`, `roles`, `user_role`.

---

## 🔒 **6. SecurityContextHolder & SecurityContext**

* Sau khi xác thực thành công hoặc validate token:

    * Spring tạo `Authentication` object (chứa principal, roles, ...).
    * Lưu vào `SecurityContext`.
    * `SecurityContext` lại được lưu trong `SecurityContextHolder` (theo cơ chế **ThreadLocal**, riêng biệt cho từng request).

→ Các lớp Controller, Service có thể truy cập user hiện tại bằng:

```java
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
String username = auth.getName();
```

---

## 🧱 **7. Proxy & Protected Resources (Restful API)**

* Khi request được xác thực → đi qua proxy (Spring Security FilterChain).
* Spring kiểm tra **quyền truy cập (authorization)**:

    * So khớp với cấu hình `@PreAuthorize("hasRole('ADMIN')")`,
    * Hoặc cấu hình trong `SecurityConfig` (`.antMatchers("/admin/**").hasRole("ADMIN")`).

Nếu người dùng có quyền → request được chuyển tiếp đến controller.
Nếu không → trả về `403 Forbidden`.

---

## 🚨 **8. AuthenticationEntryPoint**

* Khi có lỗi xác thực (token sai, thiếu, hết hạn, hoặc password sai),
  → Spring ném `AuthenticationException`.
* `AuthenticationEntryPoint` bắt lỗi và trả response JSON như:

  ```json
  { "message": "Unauthorized", "status": 401 }
  ```

---

## 🔁 **9. Tóm tắt luồng hoạt động**

### 🟢 **Lần đầu đăng nhập**

1. Client gửi username/password → AuthenticationManager.
2. Provider gọi UserDetailsService → DB → kiểm tra mật khẩu.
3. Nếu đúng → JwtProvider sinh JWT → gửi về client.

### 🔵 **Các request tiếp theo**

1. Client gửi JWT trong header.
2. Filter kiểm tra token → JwtProvider parse → xác thực user.
3. Authentication được lưu vào SecurityContext.
4. Spring kiểm tra quyền → cho phép hoặc chặn truy cập.

---

## 🧠 **10. Sơ đồ khái quát lại (dễ nhớ)**

```
Client
  ↓
HTTP Request (Authorization: Bearer token)
  ↓
JwtAuthTokenFilter
  → JwtProvider.validate()
  → UserDetailsService.loadUserByUsername()
  → SecurityContextHolder.setAuthentication()
  ↓
SecurityContext
  ↓
Spring Security Proxy (Authorization check)
  ↓
RestController (if authorized)
  ↓
Response
```

---

👉 **Tóm lại:**

> Sơ đồ này mô tả rõ ràng pipeline xử lý của **Spring Security + JWT**,
> nơi mỗi lớp có nhiệm vụ riêng biệt — từ xác thực, quản lý context, đến ủy quyền truy cập REST API.

---


## 🧩 Sơ đồ tổng thể (diễn giải lại)

Hình bạn gửi có các khối chính:

```
[HTTP Request]
     ↓
[JwtAuthTokenFilter] ──→ [JwtProvider]
     ↓
[AuthenticationManager] ──→ [UserDetailsService] ──→ [MySQL]
     ↓
[SecurityContextHolder] ──→ [SecurityContext]
     ↓
[Proxy] ──→ [Restful API]
```

---

## 🪄 GIẢI THÍCH CHI TIẾT (CÓ CHÚ THÍCH TIẾNG VIỆT)

| **Thành phần**                                           | **Vai trò / Chú thích tiếng Việt**                                                                                                                                                                                                                                                  |
| -------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 🧑‍💻 **Client (HTTP Request)**                          | Gửi yêu cầu HTTP đến server (ví dụ: `GET /api/users` hoặc `POST /login`). <br> Nếu đã đăng nhập trước đó, token JWT sẽ nằm trong Header: `Authorization: Bearer <token>`.                                                                                                           |
| 🧱 **Spring Security**                                   | Là “hàng rào bảo vệ” bao quanh toàn bộ hệ thống — mọi request đều phải đi qua đây.                                                                                                                                                                                                  |
| 🧩 **JwtAuthTokenFilter (extends OncePerRequestFilter)** | - Bộ lọc đầu tiên trong chuỗi filter của Spring Security. <br>- Lấy token JWT từ header. <br>- Gọi `JwtProvider` để xác minh token. <br>- Nếu hợp lệ, tạo `Authentication` và lưu vào `SecurityContextHolder`. <br>- Nếu không hợp lệ, ném lỗi để `AuthenticationEntryPoint` xử lý. |
| 🔐 **JwtProvider (hoặc JwtUtils)**                       | - Xử lý các thao tác với JWT (JSON Web Token). <br>- Chịu trách nhiệm: <br>   • Sinh token khi người dùng đăng nhập thành công. <br>   • Giải mã (parse) token từ request. <br>   • Kiểm tra tính hợp lệ, hạn token, chữ ký.                                                        |
| 🔑 **AuthenticationManager**                             | - Trung tâm quản lý xác thực. <br>- Khi người dùng đăng nhập (username/password), nó sẽ kiểm tra thông tin bằng cách gọi **DaoAuthenticationProvider**.                                                                                                                             |
| 🧰 **DaoAuthenticationProvider**                         | - Là “người thực hiện thật sự” việc xác minh tài khoản. <br>- Gọi `UserDetailsService` để tải thông tin user từ DB. <br>- So sánh mật khẩu nhập vào với mật khẩu đã mã hoá (bằng `PasswordEncoder`). <br>- Nếu hợp lệ → tạo đối tượng `Authentication`.                             |
| 👤 **UserDetailsService**                                | - Interface chuẩn của Spring. <br>- Chịu trách nhiệm tải thông tin người dùng (UserDetails) từ database.                                                                                                                                                                            |
| 🧾 **UserDetails (UserPrincipal)**                       | - Đối tượng chứa thông tin người dùng: `username`, `password`, `roles`. <br>- Dữ liệu này sẽ được Spring Security dùng trong toàn bộ request.                                                                                                                                       |
| 🗄️ **MySQL Database**                                   | - Lưu các bảng `user`, `role`, `user_role`. <br>- `UserDetailsService` đọc dữ liệu ở đây để xác thực và cấp quyền.                                                                                                                                                                  |
| 📦 **SecurityContextHolder / SecurityContext**           | - Sau khi người dùng được xác thực thành công, Spring lưu thông tin đó vào `SecurityContextHolder`. <br>- Thông tin này tồn tại trong suốt request để biết ai đang đăng nhập và có quyền gì.                                                                                        |
| 🧭 **Proxy (Spring Security Filter Chain)**              | - Là lớp trung gian “gác cổng”. <br>- Mỗi request đi qua đây sẽ được kiểm tra xem user có quyền truy cập endpoint đó không (`@PreAuthorize`, `.antMatchers`).                                                                                                                       |
| 🚀 **Restful API (Protected Resource)**                  | - Là tài nguyên (controller / endpoint) bạn muốn bảo vệ. <br>- Nếu user có quyền → API được truy cập. <br>- Nếu không → trả về `403 Forbidden`.                                                                                                                                     |
| 🚨 **AuthenticationEntryPoint**                          | - Khi token sai, hết hạn, hoặc không có quyền → nó bắt lỗi và trả về JSON thông báo 401/403.                                                                                                                                                                                        |

---

## 🔁 LUỒNG XỬ LÝ (CÓ GIẢI THÍCH TIẾNG VIỆT)

### 🔹 **1. Khi người dùng đăng nhập**

1. Client gửi `POST /login` với `username`, `password`.
2. `AuthenticationManager` gọi `UserDetailsService` → đọc user từ DB.
3. `PasswordEncoder` kiểm tra mật khẩu.
4. Nếu hợp lệ → `JwtProvider` sinh ra JWT token.
5. Trả token cho client → client lưu token để dùng cho các request sau.

---

### 🔹 **2. Khi người dùng truy cập API**

1. Client gửi request kèm `Authorization: Bearer <JWT>`.
2. `JwtAuthTokenFilter` đọc token → gửi đến `JwtProvider` để kiểm tra.
3. Nếu token hợp lệ:

    * Parse ra username → gọi `UserDetailsService` → lấy thông tin user.
    * Tạo `Authentication` → lưu vào `SecurityContextHolder`.
4. `SecurityContext` chứa thông tin user hiện tại.
5. `Spring Security Proxy` kiểm tra quyền:

    * Nếu user có role phù hợp → đi tiếp đến Controller.
    * Nếu không có quyền → `403 Forbidden`.
6. Controller xử lý logic và trả kết quả cho client.

---

## 📘 Ví dụ thực tế

```text
[1] POST /login
→ AuthenticationManager xác thực username/password
→ JwtProvider sinh token "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
→ Trả về client

[2] GET /api/users
→ Header: Authorization: Bearer eyJhbGciOiJIUzI1Ni...
→ JwtAuthTokenFilter validate token
→ Lưu user vào SecurityContext
→ Kiểm tra quyền truy cập API
→ Trả về danh sách users
```

---

## 🧠 Tóm tắt dễ nhớ

| **Bước** | **Thành phần chính**  | **Chức năng**              |
| -------- | --------------------- | -------------------------- |
| 1        | JwtAuthTokenFilter    | Chặn request, lấy JWT      |
| 2        | JwtProvider           | Giải mã, xác minh token    |
| 3        | UserDetailsService    | Tải thông tin user từ DB   |
| 4        | AuthenticationManager | Kiểm tra username/password |
| 5        | SecurityContextHolder | Lưu user đã xác thực       |
| 6        | Proxy                 | Kiểm tra quyền truy cập    |
| 7        | Rest API              | Xử lý logic, trả dữ liệu   |

---

UsernamPassAuthToken chỉ là 1 implement của interface Authentication ? và luồng nó như thế này - khi đnăg nhập lần đầu thì nó qua JWTAuthTokenFilter để kiêm tra xác thực - lúc này nó gọi AuthenManager -> Dao -> xác thực dữ liệu và nếu đúng thì Authenmanager trả về 1 Authencation đã xác thực - > JWT Provider cung cấp 1 token để trả về - > lúc sau truy  cập các API khác kèm token thì khi qua JWTProvider nó sẽ kiểm tra và lưu đối tượng đó vvafo ContextHolder để đảm bào mỗi luồng chỉ có 1 đối tuognjw chạy ?

## 🔹 1️⃣ `UsernamePasswordAuthenticationToken` chỉ là 1 implement của `Authentication`

Đúng 100%.
`Authentication` là **interface gốc** của Spring Security, đại diện cho mọi thông tin xác thực trong hệ thống.

👉 Các implement phổ biến gồm:

* `UsernamePasswordAuthenticationToken` → dùng cho xác thực username/password.
* `JwtAuthenticationToken` → dùng cho xác thực bằng JWT.
* `AnonymousAuthenticationToken` → dùng cho người dùng chưa đăng nhập.

```java
public class UsernamePasswordAuthenticationToken 
       extends AbstractAuthenticationToken 
       implements Authentication {
    private final Object principal;  // username hoặc UserDetails
    private Object credentials;      // password
}
```

---

## 🔹 2️⃣ Luồng đăng nhập lần đầu (Authentication)

Khi client gọi API `/login` với username & password:

### 🔸 (1) Request → `JwtAuthTokenFilter`

* Filter bắt request và **kiểm tra header Authorization**.
* Nếu **chưa có token**, nghĩa là user đang **đăng nhập lần đầu** → request đi vào `AuthenticationManager`.

---

### 🔸 (2) `AuthenticationManager` → `DaoAuthenticationProvider`

* Tạo 1 `UsernamePasswordAuthenticationToken` (chưa xác thực).
* `AuthenticationManager` chuyển token này sang `DaoAuthenticationProvider`.
* `DaoAuthenticationProvider` gọi `UserDetailsService` để **load thông tin người dùng từ database (MySQL)**.
* Sau đó dùng `PasswordEncoder` để **so sánh password**.

👉 Nếu đúng:

* Trả về một đối tượng `Authentication` (đã authenticated = true).
* Gồm đầy đủ `UserDetails` và `GrantedAuthorities` (quyền hạn).

---

### 🔸 (3) `JwtProvider` sinh JWT token

* Dựa trên thông tin trong `Authentication`, `JwtProvider` **generate** 1 token JWT.

* Token chứa:

    * `sub`: username
    * `exp`: thời hạn
    * `roles`: danh sách quyền hạn

* Token được trả về cho client → client **tự lưu vào localStorage hoặc cookie**.

---

## 🔹 3️⃣ Các request tiếp theo (Authorization)

Khi client gọi các API khác (VD: `/api/products`):

### 🔸 (1) `JwtAuthTokenFilter`

* Filter kiểm tra `Authorization: Bearer <token>`.
* Gửi token sang `JwtProvider` để **validate + parse**.
* Nếu hợp lệ → lấy `username` ra → gọi `UserDetailsService.loadUserByUsername()` để lấy chi tiết người dùng.

---

### 🔸 (2) Tạo `Authentication` và lưu vào `SecurityContextHolder`

* Tạo lại đối tượng `UsernamePasswordAuthenticationToken` (đã xác thực).
* Đặt vào `SecurityContextHolder`.

```java
SecurityContextHolder.getContext().setAuthentication(authentication);
```

> 🔹 Mỗi thread xử lý request có **1 SecurityContext riêng biệt**
> → đảm bảo **mỗi luồng chỉ có 1 người dùng (principal)** → thread-safe và độc lập giữa các request.

---

### 🔸 (3) Tiếp tục đến controller

* Khi qua filter thành công, request đi tiếp vào các controller REST API.
* Các annotation như `@PreAuthorize("hasRole('ADMIN')")` sẽ kiểm tra quyền dựa vào `Authentication` trong `SecurityContextHolder`.

---

## 🧭 Tóm tắt luồng hoạt động

| Giai đoạn          | Thành phần chính                                                           | Chức năng                                 |
| ------------------ | -------------------------------------------------------------------------- | ----------------------------------------- |
| Đăng nhập          | `AuthenticationManager`, `DaoAuthenticationProvider`, `UserDetailsService` | Xác thực username/password                |
| Sinh token         | `JwtProvider`                                                              | Tạo JWT token chứa thông tin người dùng   |
| Request API        | `JwtAuthTokenFilter`                                                       | Lọc request, xác thực token               |
| Lưu context        | `SecurityContextHolder`                                                    | Lưu thông tin xác thực cho luồng hiện tại |
| Kiểm soát truy cập | `@PreAuthorize`, `GrantedAuthority`                                        | Phân quyền truy cập tài nguyên            |

---

Nếu bạn muốn, tôi có thể **vẽ lại UML sequence** chi tiết **2 giai đoạn:**

* Đăng nhập lần đầu (có username/password)
* Truy cập API bằng JWT token

→ Hai luồng song song để so sánh trực quan như slide học.
Bạn có muốn mình vẽ luôn không?
