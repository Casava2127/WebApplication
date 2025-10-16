Dưới đây là giải thích từng dòng mã trong file `Employee`:

```java
package ra.crud.model;
```
- **Khai báo package**: Tệp này thuộc package `ra.crud.model`, nơi chứa các class đại diện cho các bảng trong cơ sở dữ liệu (entity).

---

### **Import các thư viện cần thiết**
```java
import lombok.AllArgsConstructor; // Tự động sinh constructor có tham số
import lombok.Getter; // Tự động sinh các getter cho các thuộc tính
import lombok.NoArgsConstructor; // Tự động sinh constructor không tham số
import lombok.Setter; // Tự động sinh các setter cho các thuộc tính
import org.springframework.format.annotation.DateTimeFormat; // Định dạng ngày tháng trong dữ liệu

import javax.persistence.*; // Các annotation để định nghĩa entity và ánh xạ với bảng trong cơ sở dữ liệu
import java.util.Date; // Thư viện để sử dụng kiểu dữ liệu ngày tháng
```
---

### **Khai báo class là một entity**
```java
@Entity // Đánh dấu class là một entity (đại diện cho bảng trong cơ sở dữ liệu)
@Table(name = "Employee") // Ánh xạ entity này đến bảng `Employee` trong cơ sở dữ liệu
@NoArgsConstructor // Tự động sinh constructor không tham số
@AllArgsConstructor // Tự động sinh constructor có tất cả các tham số
@Getter // Tự động sinh getter cho tất cả các thuộc tính
@Setter // Tự động sinh setter cho tất cả các thuộc tính
public class Employee {
```
- **Chức năng**:
    - `@Entity`: Biến class `Employee` thành một entity JPA, được quản lý bởi Hibernate.
    - `@Table(name = "Employee")`: Ánh xạ class này với bảng `Employee` trong cơ sở dữ liệu.
    - Các annotation của **Lombok** giúp giảm mã lặp lại bằng cách tự động sinh constructor, getter và setter.

---

### **Định nghĩa các thuộc tính và ánh xạ với cột trong bảng**

#### **1. Thuộc tính `empId`**
```java
@Id // Đánh dấu thuộc tính này là khóa chính của bảng
@Column(name = "emp_id", columnDefinition = "char(5)") // Ánh xạ cột `emp_id` với kiểu `char(5)`
private String empId;
```
- **`@Id`**: Biến `empId` thành khóa chính (primary key) trong bảng `Employee`.
- **`@Column`**:
    - `name = "emp_id"`: Tên cột trong bảng cơ sở dữ liệu là `emp_id`.
    - `columnDefinition = "char(5)"`: Kiểu dữ liệu của cột là `char(5)`.

---

#### **2. Thuộc tính `empName`**
```java
@Column(name = "emp_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
private String empName;
```
- **`@Column`**:
    - `name = "emp_name"`: Ánh xạ với cột `emp_name` trong cơ sở dữ liệu.
    - `columnDefinition = "varchar(100)"`: Kiểu dữ liệu là chuỗi ký tự tối đa 100 ký tự.
    - `nullable = false`: Không được để trống (NOT NULL).
    - `unique = true`: Giá trị phải là duy nhất trong bảng (không trùng lặp).

---

#### **3. Thuộc tính `bod`**
```java
@Column(name = "emp_bod") // Ánh xạ với cột `emp_bod`
@DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng dữ liệu ngày tháng (theo định dạng `yyyy-MM-dd`)
private Date bod;
```
- **`@Column`**: Ánh xạ với cột `emp_bod` trong cơ sở dữ liệu.
- **`@DateTimeFormat`**: Xác định định dạng ngày tháng khi nhập liệu hoặc hiển thị (`yyyy-MM-dd`).

---

#### **4. Thuộc tính `salary`**
```java
@Column(name = "emp_salary", columnDefinition = "float check(emp_salary>0)", nullable = false)
private float salary;
```
- **`@Column`**:
    - `name = "emp_salary"`: Ánh xạ với cột `emp_salary` trong cơ sở dữ liệu.
    - `columnDefinition = "float check(emp_salary>0)"`: Kiểu dữ liệu `float`, có ràng buộc giá trị lớn hơn 0 (`CHECK` constraint).
    - `nullable = false`: Không được để trống (NOT NULL).

---

#### **5. Thuộc tính `status`**
```java
@Column(name = "emp_status") // Ánh xạ với cột `emp_status`
private boolean status;
```
- **`@Column`**:
    - `name = "emp_status"`: Ánh xạ với cột `emp_status` trong cơ sở dữ liệu.
- **Kiểu dữ liệu**: `boolean` biểu thị trạng thái nhân viên (ví dụ: `true` cho đang làm việc, `false` cho nghỉ việc).

---

#### **6. Thuộc tính `department`**
```java
@ManyToOne // Thiết lập mối quan hệ Nhiều-Nhiều giữa Employee và Department
@JoinColumn(name = "dept_id", referencedColumnName = "dept_id") 
private Department department;
```
- **`@ManyToOne`**: Thiết lập quan hệ `Nhiều-Nhiều` (Many-to-One) giữa `Employee` và `Department`.
    - Một nhân viên (`Employee`) thuộc một phòng ban (`Department`).
- **`@JoinColumn`**:
    - `name = "dept_id"`: Tên cột trong bảng `Employee` dùng làm khóa ngoại.
    - `referencedColumnName = "dept_id"`: Cột `dept_id` trong bảng `Department` là khóa chính liên kết.

---

### **Tóm tắt chức năng của file**

- **Vai trò**:  
  Class `Employee` đại diện cho bảng `Employee` trong cơ sở dữ liệu. Nó ánh xạ các cột trong bảng thành các thuộc tính trong Java.

- **Luồng hoạt động**:
    1. Khi ứng dụng chạy, Hibernate sử dụng thông tin từ class này để:
        - Tạo bảng `Employee` (nếu chưa có).
        - Quản lý các đối tượng `Employee` như lưu, cập nhật, xóa, hoặc truy vấn.
    2. Các thuộc tính như `empId`, `empName` được ánh xạ trực tiếp với cột tương ứng trong cơ sở dữ liệu.
    3. Quan hệ với bảng `Department` được thiết lập thông qua khóa ngoại (`dept_id`).
    4. Dữ liệu ngày tháng được định dạng theo chuẩn `yyyy-MM-dd` nhờ `@DateTimeFormat`.

- **Ý nghĩa**:  
  File này là trung tâm của ORM (Object-Relational Mapping), giúp tự động chuyển đổi giữa bảng `Employee` trong cơ sở dữ liệu và đối tượng `Employee` trong Java.
- Dưới đây là file code đã được bổ sung giải thích chi tiết dưới dạng comment sau mỗi dòng lệnh:

```java
package ra.crud.model; // Khai báo package chứa các class đại diện cho bảng trong cơ sở dữ liệu

// Import các thư viện cần thiết
import lombok.*; // Import các annotation của Lombok để tự động sinh mã
import javax.persistence.*; // Import các annotation để định nghĩa entity và ánh xạ với bảng cơ sở dữ liệu
import java.util.ArrayList; // Thư viện để tạo danh sách (List)
import java.util.List; // Thư viện đại diện cho kiểu danh sách

@Entity // Đánh dấu class là một entity (đại diện cho bảng trong cơ sở dữ liệu)
@Table(name = "Department") // Ánh xạ entity này đến bảng `Department` trong cơ sở dữ liệu
@NoArgsConstructor // Tự động sinh constructor không tham số
@AllArgsConstructor // Tự động sinh constructor có tất cả các tham số
@Getter // Tự động sinh getter cho tất cả các thuộc tính
@Setter // Tự động sinh setter cho tất cả các thuộc tính
public class Department { // Class `Department` đại diện cho bảng `Department` trong cơ sở dữ liệu

    @Id // Đánh dấu thuộc tính này là khóa chính của bảng
    @Column(name = "dept_id") // Ánh xạ cột `dept_id` trong bảng cơ sở dữ liệu
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Tự động tăng giá trị của khóa chính (AUTO_INCREMENT trong SQL)
    private int deptId; // Khóa chính của bảng `Department`

    @Column(name = "dept_name", columnDefinition = "varchar(100)", unique = true, nullable = false) 
    // Ánh xạ với cột `dept_name`, kiểu chuỗi, giá trị phải duy nhất và không được để trống
    private String deptName; // Tên phòng ban

    @Column(name = "dept_description", columnDefinition = "text") 
    // Ánh xạ với cột `dept_description`, kiểu dữ liệu `text` trong cơ sở dữ liệu
    private String description; // Mô tả phòng ban

    @Column(name = "dept_status") // Ánh xạ với cột `dept_status`
    private boolean status; // Trạng thái phòng ban (true = hoạt động, false = không hoạt động)

    @OneToMany(mappedBy = "department") 
    // Thiết lập mối quan hệ 1-Nhiều giữa Department và Employee, 
    // `mappedBy` chỉ ra rằng `department` là bên được quản lý trong mối quan hệ
    private List<Employee> listEmloyees = new ArrayList<>(); 
    // Danh sách các nhân viên thuộc phòng ban (quan hệ 1-Nhiều)
}
```

### **Giải thích chi tiết**
1. **`@Entity` và `@Table`**:
  - Biến class `Department` thành một entity JPA, ánh xạ với bảng `Department` trong cơ sở dữ liệu.

2. **Thuộc tính `deptId`**:
  - **`@Id`**: Biến `deptId` thành khóa chính của bảng.
  - **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Sử dụng chiến lược `IDENTITY`, khóa chính sẽ được tự động tăng trong cơ sở dữ liệu (thường là `AUTO_INCREMENT` trong MySQL).
  - Ánh xạ với cột `dept_id`.

3. **Thuộc tính `deptName`**:
  - Ánh xạ với cột `dept_name`, kiểu chuỗi (`varchar(100)`).
  - **`unique = true`**: Giá trị phải là duy nhất trong bảng.
  - **`nullable = false`**: Không được để trống (NOT NULL).

4. **Thuộc tính `description`**:
  - Ánh xạ với cột `dept_description`, kiểu dữ liệu `text` trong cơ sở dữ liệu.
  - Dùng để lưu mô tả chi tiết về phòng ban.

5. **Thuộc tính `status`**:
  - Ánh xạ với cột `dept_status`, kiểu dữ liệu `boolean`.
  - Đại diện trạng thái phòng ban (ví dụ: `true` cho hoạt động, `false` cho không hoạt động).

6. **Quan hệ `One-to-Many` với `Employee`**:
  - **`@OneToMany(mappedBy = "department")`**:
    - Mối quan hệ 1-Nhiều giữa `Department` và `Employee`.
    - `mappedBy = "department"` chỉ ra rằng quan hệ này được quản lý bởi thuộc tính `department` trong class `Employee`.
  - **`List<Employee> listEmployees`**:
    - Danh sách các đối tượng `Employee` thuộc về một `Department`.

### **Tóm tắt chức năng**
- **Vai trò**:  
  Class `Department` ánh xạ với bảng `Department` trong cơ sở dữ liệu, đại diện cho phòng ban trong tổ chức.

- **Luồng hoạt động**:
  1. Hibernate quản lý các đối tượng `Department` và ánh xạ với bảng `Department`.
  2. Khi thêm, sửa, xóa hoặc truy vấn, Hibernate tự động thực hiện các thao tác với bảng `Department`.
  3. Mối quan hệ 1-Nhiều với bảng `Employee` giúp truy xuất danh sách nhân viên thuộc mỗi phòng ban một cách dễ dàng.

- **Ý nghĩa**:  
  Giúp xây dựng ứng dụng quản lý nhân viên và phòng ban dễ dàng hơn nhờ tự động hóa các thao tác ORM.