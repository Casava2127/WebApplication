package ra.crud.controller;
// Đặt tên cho package chứa các lớp điều khiển (controller), trong đó `DepartmentController` sẽ xử lý các yêu cầu HTTP liên quan đến phòng ban.

import org.springframework.beans.factory.annotation.Autowired;
// Import annotation `@Autowired` từ Spring để tự động tiêm các bean vào trong lớp này.

import org.springframework.stereotype.Controller;
// Import annotation `@Controller` từ Spring để đánh dấu lớp này là một controller, giúp Spring nhận diện lớp này như một phần của MVC.

import org.springframework.ui.Model;
// Import `Model` từ Spring, đối tượng dùng để truyền dữ liệu từ controller sang view.

import org.springframework.web.bind.annotation.GetMapping;
// Import `@GetMapping` từ Spring để đánh dấu phương thức này xử lý các yêu cầu HTTP GET.

import org.springframework.web.bind.annotation.PostMapping;
// Import `@PostMapping` từ Spring để đánh dấu phương thức này xử lý các yêu cầu HTTP POST.

import org.springframework.web.bind.annotation.RequestMapping;
// Import `@RequestMapping` từ Spring để định nghĩa URL mà controller này sẽ xử lý.

import ra.crud.model.Department;
// Import đối tượng `Department`, model đại diện cho bảng phòng ban trong cơ sở dữ liệu.

import ra.crud.service.DepartmentService;
// Import interface `DepartmentService`, sẽ cung cấp các phương thức nghiệp vụ về phòng ban (Department).

import java.util.List;
// Import lớp `List`, để sử dụng kiểu dữ liệu danh sách cho kết quả trả về từ các phương thức.

@Controller
@RequestMapping("/departmentController")
// Đánh dấu lớp này là một controller, sẽ xử lý các yêu cầu HTTP.
// `@RequestMapping("/departmentController")` chỉ định rằng các yêu cầu HTTP bắt đầu với `/departmentController` sẽ được xử lý bởi lớp này.

public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    // Tiêm đối tượng `DepartmentService` vào controller này để sử dụng các phương thức nghiệp vụ, như tìm kiếm, cập nhật phòng ban.

    @GetMapping("/findAll")
    public String findALlDepartment(Model model) {
        // Phương thức xử lý các yêu cầu GET tại URL `/departmentController/findAll`.
        // Trả về danh sách tất cả các phòng ban và hiển thị trên view "departments".

        List<Department> listDepartments = departmentService.findAll();
        // Gọi phương thức `findAll()` trong `DepartmentService` để lấy danh sách tất cả các phòng ban.

        model.addAttribute("listDepartments", listDepartments);
        // Thêm danh sách `listDepartments` vào đối tượng `model`, để có thể sử dụng trong view.

        return "departments";
        // Trả về tên view "departments" để hiển thị danh sách phòng ban. View này sẽ hiển thị danh sách các phòng ban.
    }

    @GetMapping("/initUpdate")
    public String initUpdateDepartment(Model model, int deptId) {
        // Phương thức xử lý các yêu cầu GET tại URL `/departmentController/initUpdate` để khởi tạo trang cập nhật phòng ban.
        // Lấy thông tin phòng ban theo `deptId` để hiển thị trên form cập nhật.

        Department deptUpdate = departmentService.findById(deptId);
        // Gọi phương thức `findById(deptId)` trong `DepartmentService` để tìm phòng ban theo `deptId`.

        model.addAttribute("deptUpdate", deptUpdate);
        // Thêm đối tượng `deptUpdate` vào đối tượng `model`, để truyền thông tin phòng ban cần cập nhật vào form.

        return "updateDepartment";
        // Trả về tên view "updateDepartment" để hiển thị form cập nhật phòng ban.
    }

    @PostMapping("/update")
    public String updateDepartment(Department deptUpdate) {
        // Phương thức xử lý các yêu cầu POST tại URL `/departmentController/update` khi người dùng submit form cập nhật phòng ban.

        boolean result = departmentService.update(deptUpdate);
        // Gọi phương thức `update(deptUpdate)` trong `DepartmentService` để cập nhật thông tin phòng ban trong cơ sở dữ liệu.

        if (result) {
            return "redirect:findAll";
            // Nếu cập nhật thành công, chuyển hướng người dùng về URL `/departmentController/findAll` để hiển thị danh sách phòng ban cập nhật.
        }
        return "error";
        // Nếu cập nhật không thành công, trả về view "error" để hiển thị thông báo lỗi.
    }
    @GetMapping("/delete")
    public String deleteDepartment(int deptId) {
        // Phương thức xử lý các yêu cầu POST tại URL `/departmentController/update` khi người dùng submit form cập nhật phòng ban.
        boolean result = departmentService.delete(deptId);
        // Gọi phương thức `update(deptUpdate)` trong `DepartmentService` để cập nhật thông tin phòng ban trong cơ sở dữ liệu.
        if (result) {
            return "redirect:findAll";
            // Nếu cập nhật thành công, chuyển hướng người dùng về URL `/departmentController/findAll` để hiển thị danh sách phòng ban cập nhật.
        }
        return "error";
    }
}
