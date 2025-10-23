
## 🎯 1. Vấn đề gốc

Trong database, bạn thường có bảng `roles` như sau:

| user_id | role_name  |
| ------- | ---------- |
| 1       | ROLE_ADMIN |
| 1       | ROLE_USER  |
| 2       | ROLE_USER  |

Entity `User` của bạn có thể có danh sách:

```java
List<Role> roles;
```

Nhưng **Spring Security không hiểu class `Role` của bạn**, nó chỉ hiểu interface:

```java
org.springframework.security.core.GrantedAuthority
```

→ Vì thế ta **phải chuyển đổi** từng `Role` sang `GrantedAuthority`.

---

## 🧩 2. Tại sao cần *vòng lặp*?

Giả sử `User` có nhiều quyền (ROLE_ADMIN, ROLE_USER).
Spring Security muốn có **một danh sách các quyền** theo dạng mà nó hiểu, ví dụ:

```java
Collection<? extends GrantedAuthority> authorities = [
SimpleGrantedAuthority("ROLE_ADMIN"),
SimpleGrantedAuthority("ROLE_USER")
];
```

Mỗi quyền (`ROLE_ADMIN`, `ROLE_USER`) → là một **đối tượng riêng** kiểu `GrantedAuthority`.
Do đó, ta cần **vòng lặp để tạo ra từng đối tượng**.

---

### 🔍 Code minh họa:

```java
Set<GrantedAuthority> authorities = new HashSet<>();
for (Role role : user.getRoles()) {
        authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }
```

Ở đây:

* `role.getRolename()` là chuỗi “ROLE_ADMIN”, “ROLE_USER”, v.v.
* `SimpleGrantedAuthority` là lớp hiện thực (`implements`) của interface `GrantedAuthority`.
* `HashSet` dùng để tránh trùng lặp.

---

## 🔐 3. Spring dùng danh sách đó để làm gì?

Sau khi `UserDetailService` trả về `UserPrinciple` chứa danh sách `authorities`,
Spring sẽ **lưu các quyền đó trong `SecurityContext`** khi người dùng đăng nhập thành công.

Sau đó ở mọi request, Spring có thể kiểm tra quyền như:

```java
@PreAuthorize("hasRole('ADMIN')")
```

→ Spring sẽ so sánh chuỗi `"ROLE_ADMIN"` trong danh sách `GrantedAuthority`.

---

## 🧠 4. Tóm gọn bản chất

| Mục tiêu                                         | Giải thích                                 |
| ------------------------------------------------ | ------------------------------------------ |
| DB có `Role` entity                              | Spring không hiểu nó trực tiếp             |
| Spring chỉ hiểu `GrantedAuthority`               | interface thể hiện quyền                   |
| Vì vậy phải chuyển từng Role → GrantedAuthority  | dùng `for` loop để tạo đối tượng tương ứng |
| Kết quả là danh sách quyền mà Spring có thể dùng | để kiểm tra, ủy quyền, bảo vệ endpoint     |

---

### 🌳 Ví dụ trực quan

Giống như bạn có danh sách *tên nghề nghiệp*:

> ["Bác sĩ", "Kỹ sư", "Giáo viên"]

Nhưng hệ thống chỉ hiểu đối tượng `Job(title)`.
Bạn phải chuyển từng tên nghề thành một **đối tượng `Job` riêng**:

```java
for (String jobName : jobNames) {
        jobs.add(new Job(jobName));
        }
```

Spring Security cũng vậy với “quyền”.

---
