### **Tóm tắt Cấu trúc Thư mục Dự án Java EE Mô hình MVC:**

Dự án của bạn sử dụng mô hình MVC (Model-View-Controller) để tổ chức các mã nguồn và tài nguyên web, đồng thời kết nối với MySQL để truy xuất dữ liệu người dùng. Dưới đây là cấu trúc thư mục của dự án và chức năng từng thư mục, tệp trong dự án:

### **Cấu trúc Thư mục Dự án**

```
MyJavaEEProject/
├── build.gradle                # Cấu hình Gradle cho dự án
└── src/
    └── main/
        ├── java/
        │   └── ra/
        │       └── com/
        │           └── example/
        │               ├── controller/
        │               │   └── UserController.java      # Xử lý yêu cầu HTTP và quản lý các yêu cầu tới View
        │               ├── model/
        │               │   ├── User.java                # Đối tượng User với các thuộc tính như id, name, email
        │               │   └── UserService.java          # Lớp xử lý các nghiệp vụ liên quan đến User, bao gồm việc truy vấn CSDL
        │               └── view/
        │                   └── user.jsp                 # Tệp JSP hiển thị dữ liệu người dùng trong bảng
        ├── resources/
        └── webapp/
            ├── WEB-INF/
            │   └── web.xml                               # Cấu hình servlet và ánh xạ các URL
            └── user.jsp                                  # Tệp JSP thực sự được phục vụ từ máy chủ web
```

---

### **Chức năng các Thư mục và Tệp trong Dự án**

#### **1. Thư mục `java/ra/com/example/controller/`**
- **`UserController.java`**: Đây là **Controller** trong mô hình MVC. Nó xử lý các yêu cầu HTTP từ người dùng, gọi các phương thức trong **Model** (UserService), và chuyển tiếp dữ liệu sang **View** (JSP) để hiển thị.

#### **2. Thư mục `java/ra/com/example/model/`**
- **`User.java`**: Đây là lớp **Model** đại diện cho đối tượng người dùng, với các thuộc tính như `id`, `name`, `email`, cùng với các phương thức getter và setter.
- **`UserService.java`**: Đây là lớp xử lý các nghiệp vụ liên quan đến **User**, như lấy danh sách người dùng từ cơ sở dữ liệu MySQL.

#### **3. Thư mục `java/ra/com/example/view/`**
- **`user.jsp`**: Đây là **View** trong mô hình MVC. Tệp JSP này hiển thị thông tin người dùng trong một bảng HTML. Dữ liệu người dùng được truyền từ **Controller** (UserController) và được hiển thị trong trang web.

#### **4. Thư mục `webapp/`**
- **`web.xml`**: Tệp này cấu hình các servlet và ánh xạ URL. Servlet `UserController` sẽ được ánh xạ với URL `/user`, giúp xử lý các yêu cầu tới URL đó.
- **`user.jsp`**: Tệp này sẽ được phục vụ từ máy chủ web. Nó hiển thị danh sách người dùng. Nếu bạn muốn bảo vệ trang này, bạn có thể đặt nó trong thư mục `WEB-INF`.

#### **5. Tệp `build.gradle`**
- Cấu hình Gradle của dự án, bao gồm các phụ thuộc như MySQL connector, Servlet API, và các thư viện khác cần thiết cho dự án.

---

### **Cách Chạy Dự Án và Quy Trình Chạy**

#### **1. Cấu hình Cơ sở Dữ liệu MySQL**
- **Tạo cơ sở dữ liệu và bảng**:
    1. Mở MySQL và tạo một cơ sở dữ liệu mới:

       ```sql
       CREATE DATABASE yourdatabase;
       USE yourdatabase;
       ```

    2. Tạo bảng `users` trong cơ sở dữ liệu và chèn một vài bản ghi mẫu:

       ```sql
       CREATE TABLE users (
           id INT AUTO_INCREMENT PRIMARY KEY,
           name VARCHAR(255),
           email VARCHAR(255)
       );
  
       INSERT INTO users (name, email) VALUES ('John Doe', 'john@example.com');
       INSERT INTO users (name, email) VALUES ('Jane Smith', 'jane@example.com');
       ```

#### **2. Cấu hình `build.gradle`**
- **Cấu hình phụ thuộc trong `build.gradle`**:
  Đảm bảo bạn đã thêm phụ thuộc JDBC MySQL và Servlet API vào tệp `build.gradle`:

  ```gradle
  dependencies {
      implementation 'mysql:mysql-connector-java:8.0.30'
      implementation 'javax.servlet:javax.servlet-api:4.0.1'
      implementation 'org.apache.commons:commons-dbcp2:2.9.0'
  }
  ```

#### **3. Cấu hình Web Server (Tomcat)**
- Cấu hình máy chủ web như **Tomcat** trong IDE (IntelliJ IDEA, Eclipse, v.v.), và chỉ định thư mục gốc cho ứng dụng là `src/main/webapp`.

#### **4. Chạy Dự Án**
- **Khởi động Tomcat** hoặc máy chủ web của bạn thông qua IDE và triển khai ứng dụng của bạn.
- Truy cập trang web bằng URL `http://localhost:8080/yourproject/user`. Dự án sẽ gọi đến `UserController`, xử lý dữ liệu người dùng từ cơ sở dữ liệu, và chuyển tiếp đến **View** (`user.jsp`) để hiển thị.

---

### **Quy trình chạy dự án:**

1. **Người dùng truy cập trang web**:
    - Người dùng truy cập URL `http://localhost:8080/yourproject/user`, yêu cầu được gửi đến máy chủ Tomcat.

2. **Servlet `UserController` xử lý yêu cầu**:
    - Máy chủ Tomcat chuyển yêu cầu đến **`UserController`**. Trong phương thức `doGet()`, `UserController` gọi **`UserService`** để truy xuất danh sách người dùng từ cơ sở dữ liệu MySQL.

3. **Kết nối với cơ sở dữ liệu**:
    - **`UserService`** kết nối với MySQL qua JDBC và lấy dữ liệu từ bảng `users`.

4. **Dữ liệu được truyền tới JSP**:
    - Sau khi lấy danh sách người dùng từ cơ sở dữ liệu, **`UserController`** lưu dữ liệu vào đối tượng `request` (sử dụng `request.setAttribute("users", users)`).
    - Sau đó, **`UserController`** chuyển tiếp yêu cầu đến **`user.jsp`**.

5. **Hiển thị dữ liệu trong `user.jsp`**:
    - **`user.jsp`** nhận dữ liệu từ **request** và hiển thị thông tin người dùng trong một bảng HTML.

---

### **Tóm lại Quy trình chạy Dự án**

1. Người dùng truy cập trang web.
2. Yêu cầu được chuyển đến servlet `UserController`.
3. `UserController` gọi `UserService` để lấy dữ liệu từ MySQL.
4. Dữ liệu được chuyển tiếp từ servlet đến trang JSP (`user.jsp`).
5. **`user.jsp`** hiển thị dữ liệu người dùng trong bảng HTML.

---

Với cấu trúc này, bạn có thể triển khai ứng dụng Java EE sử dụng mô hình MVC kết nối với MySQL, và hiển thị thông tin người dùng từ cơ sở dữ liệu trong ứng dụng web của mình.