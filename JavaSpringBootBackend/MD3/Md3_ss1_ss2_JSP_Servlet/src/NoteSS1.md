Để tạo một dự án Java EE trong IntelliJ IDEA và hiểu cấu trúc thư mục, ta có thể thực hiện các bước sau:

### Bước 1: Cài đặt các yêu cầu
Trước khi bắt đầu, đảm bảo:
- IntelliJ IDEA (phiên bản Ultimate hỗ trợ Java EE).
- JDK (Java Development Kit) đã được cài đặt.
- Máy chủ ứng dụng Java EE (như Apache Tomcat, GlassFish) để chạy ứng dụng.

### Bước 2: Tạo dự án Java EE trong IntelliJ IDEA
1. **Mở IntelliJ IDEA** và chọn **New Project**.
2. Trong cửa sổ **New Project**, chọn **Java Enterprise** ở bên trái.
3. **Project SDK**: Chọn JDK bạn đã cài đặt.
4. **Additional Libraries and Frameworks**:
   - Chọn các thành phần Java EE cần thiết (như **Java Servlet**, **JSP** nếu cần).
   - Nếu ứng dụng cần kết nối cơ sở dữ liệu, chọn **JPA**.
5. **Application Server**: Chọn máy chủ ứng dụng đã cài đặt (như Tomcat hoặc GlassFish). Nếu chưa có, bạn có thể thêm máy chủ mới từ IntelliJ IDEA.
6. Nhấp vào **Next**, đặt tên và chọn vị trí cho dự án, sau đó nhấn **Finish**.

### Bước 3: Cấu trúc thư mục trong dự án Java EE
Khi dự án được tạo, IntelliJ IDEA sẽ tự động tạo ra các thư mục chính cho một dự án Java EE. Dưới đây là cấu trúc thư mục và giải thích từng thư mục:

```
ProjectName/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── servlet/      # Chứa các lớp Servlet xử lý logic
│   │   │           └── model/        # Chứa các lớp đại diện cho dữ liệu (Model)
│   │   │           └── controller/   # Chứa các lớp Controller xử lý luồng logic
│   │   │           └── dao/          # Data Access Objects cho các thao tác DB
│   │   │           └── service/      # Lớp dịch vụ hỗ trợ logic nghiệp vụ
│   │   │
│   │   └── resources/                # Chứa các tài nguyên cấu hình như tệp cấu hình, 
│                                     # tài nguyên ứng dụng (file.properties)
│   │
│   └── webapp/                       # Thư mục chứa các tài nguyên web như JSP, HTML, CSS, JS
│       ├── WEB-INF/
│       │   ├── web.xml               # Tệp cấu hình chính của ứng dụng Java EE
│       │   ├── jsp/                  # Thư mục chứa các trang JSP
│       │
│       ├── static/                   # Chứa các tài nguyên tĩnh như CSS, JS
│       └── index.jsp                 # Trang JSP chính của ứng dụng
│
├── test/                             # Thư mục chứa các tệp kiểm thử (test files)
│
└── pom.xml                           # Tệp cấu hình Maven nếu dùng Maven
└── build.gradle                      # Tệp cấu hình Gradle nếu dùng Gradle
```

### Giải thích chi tiết các thư mục

1. **src/main/java/**:
   - Đây là thư mục chính cho mã nguồn Java của ứng dụng.
   - Các gói phụ (package) thường được tạo trong thư mục này để tổ chức các thành phần MVC như `servlet`, `model`, `controller`, `dao`, và `service`.

2. **src/main/java/com/example/servlet**:
   - Thư mục này chứa các lớp **Servlet** Java, được sử dụng làm Controller trong mô hình MVC để xử lý các yêu cầu HTTP từ người dùng và phản hồi bằng cách chuyển hướng đến các trang JSP.
   
3. **src/main/java/com/example/model**:
   - Thư mục này chứa các lớp **Model**, là các lớp đại diện cho dữ liệu và các đối tượng nghiệp vụ (business objects). Các lớp này thường tương ứng với các bảng trong cơ sở dữ liệu.
   
4. **src/main/java/com/example/controller**:
   - Thư mục này chứa các lớp **Controller** (có thể là các lớp xử lý riêng hoặc tích hợp với các lớp servlet) để điều hướng luồng dữ liệu trong ứng dụng.
   
5. **src/main/java/com/example/dao**:
   - Thư mục chứa các lớp **Data Access Objects (DAO)**, quản lý việc truy cập và thao tác với cơ sở dữ liệu như thêm, xóa, cập nhật, và truy vấn dữ liệu.
   
6. **src/main/java/com/example/service**:
   - Các lớp **service** chứa logic nghiệp vụ chính của ứng dụng và thường gọi đến các lớp DAO để lấy hoặc cập nhật dữ liệu trong cơ sở dữ liệu.

7. **src/main/resources/**:
   - Thư mục này chứa các tệp tài nguyên (resource files) như tệp cấu hình, file `.properties` dùng cho cấu hình, thông báo lỗi, hoặc các thiết lập khác.
   
8. **src/main/webapp/**:
   - Thư mục **webapp** chứa tất cả các tài nguyên web của ứng dụng.
   
9. **src/main/webapp/WEB-INF/**:
   - Thư mục **WEB-INF** chứa các tệp cấu hình quan trọng của ứng dụng và không cho phép truy cập trực tiếp từ trình duyệt.
   - **web.xml**: Tệp cấu hình trung tâm của Java EE, giúp định nghĩa các servlet, bộ lọc (filter), và ánh xạ URL. Các thiết lập này giúp chỉ định cách ứng dụng sẽ xử lý các yêu cầu đến.

10. **src/main/webapp/WEB-INF/jsp/**:
    - Thư mục này chứa các trang JSP, dùng làm **View** trong mô hình MVC. Các trang JSP hiển thị thông tin cho người dùng.

11. **src/main/webapp/static/**:
    - Đây là thư mục chứa các tài nguyên tĩnh như tệp **CSS**, **JavaScript**, và **hình ảnh**. Tài nguyên này sẽ được dùng trong các trang JSP hoặc HTML.

12. **index.jsp**:
    - Tệp `index.jsp` là trang khởi đầu hoặc trang chính của ứng dụng.

13. **test/**:
    - Thư mục này chứa các tệp kiểm thử (test files), dùng để viết mã kiểm thử cho các lớp trong dự án, đảm bảo chúng hoạt động đúng.

14. **pom.xml** (nếu dùng Maven) hoặc **build.gradle** (nếu dùng Gradle):
    - Đây là tệp cấu hình của **Maven** hoặc **Gradle** nếu bạn sử dụng các công cụ này để quản lý dự án. Các tệp này định nghĩa các thư viện và phụ thuộc (dependency), phiên bản Java, và các lệnh build cho dự án.

### Tóm tắt
Cấu trúc thư mục của một dự án Java EE giúp chia tách các thành phần theo mô hình MVC, giúp mã nguồn dễ quản lý, bảo trì và mở rộng.

----------------

---

## 🧩 1. Cú pháp

```java
RequestDispatcher rd = request.getRequestDispatcher("tên_đích");
rd.forward(request, response);
```

hoặc

```java
request.getRequestDispatcher("tên_đích").forward(request, response);
```

---

## 🧭 2. Mục đích

`request.getRequestDispatcher()` được dùng để **chuyển hướng (forward)** hoặc **gộp nội dung (include)** giữa **các tài nguyên nội bộ trong cùng web application**, như:

* Một file **JSP** khác,
* Một **Servlet** khác,
* Một **HTML** nội bộ.

Nói cách khác, nó cho phép **chuyển request** từ servlet hiện tại sang **một tài nguyên khác trên server** (không phải redirect ra ngoài trình duyệt).

---

## ⚙️ 3. Cách hoạt động

Khi servlet gọi:

```java
RequestDispatcher dispatcher = request.getRequestDispatcher("/page.jsp");
dispatcher.forward(request, response);
```

Trình tự là:

1. **Servlet A** nhận request từ client.
2. Servlet A **không gửi phản hồi ngay**, mà dùng `getRequestDispatcher()` để **gửi request nội bộ** đến **page.jsp**.
3. **page.jsp** xử lý và **tạo response cuối cùng** gửi lại cho client.
4. Người dùng **vẫn thấy 1 URL duy nhất trên trình duyệt** (vì forward là *nội bộ server-side*).

---

## 🧠 4. Phân biệt `forward()` và `include()`

| Phương thức                  | Ý nghĩa                                                                  | Khi nào dùng                                                                                                         |
| ---------------------------- | ------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------- |
| `forward(request, response)` | Chuyển hoàn toàn request đến tài nguyên khác, servlet hiện tại dừng lại. | Khi bạn muốn chuyển xử lý sang một trang khác (ví dụ: sau khi xử lý dữ liệu xong → chuyển đến JSP hiển thị kết quả). |
| `include(request, response)` | Gộp thêm nội dung từ trang khác vào output hiện tại (cả hai cùng chạy).  | Khi bạn muốn chèn header/footer hoặc nội dung phụ.                                                                   |

---

## 🧩 5. Ví dụ cụ thể

### 🧠 Servlet

```java
@WebServlet("/CategoriesController")
public class CategoriesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Gắn dữ liệu vào request
        request.setAttribute("categoryName", "Electronics");

        // Chuyển tiếp đến JSP để hiển thị
        request.getRequestDispatcher("categories.jsp").forward(request, response);
    }
}
```

### 📄 JSP (`categories.jsp`)

```jsp
<h1>Category: ${categoryName}</h1>
```

➡ Khi bạn truy cập `/CategoriesController`, servlet xử lý, gắn dữ liệu `"Electronics"`,
rồi **forward** sang `categories.jsp` để hiển thị ra trình duyệt.

---

## 🚫 6. Khác với `response.sendRedirect()`

| So sánh              | `forward()`            | `sendRedirect()`                         |
| -------------------- | ---------------------- | ---------------------------------------- |
| Thực hiện            | Nội bộ trên server     | Gửi lệnh redirect cho trình duyệt        |
| URL trên trình duyệt | Không đổi              | Thay đổi sang URL mới                    |
| Hiệu năng            | Nhanh hơn (vì nội bộ)  | Chậm hơn (vì có thêm 1 request HTTP mới) |
| Phạm vi              | Chỉ trong cùng web app | Có thể sang domain khác                  |

---

## ✅ Tóm tắt ngắn gọn

| Thành phần                             | Vai trò                                              |
| -------------------------------------- | ---------------------------------------------------- |
| `request.getRequestDispatcher("path")` | Lấy đối tượng điều phối request                      |
| `.forward(request, response)`          | Chuyển tiếp xử lý đến tài nguyên khác                |
| `.include(request, response)`          | Gộp thêm nội dung của tài nguyên khác                |
| Dùng khi                               | Muốn chia tách xử lý và hiển thị trong cùng ứng dụng |

---

