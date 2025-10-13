package ra.com.controller; // Khai báo package cho lớp này, giúp tổ chức mã nguồn theo module.
// servlet
import ra.com.entity.Categories; // Import lớp Categories từ package ra.com.entity.
import ra.com.service.CategoriesService; // Import interface CategoriesService từ package ra.com.service.
import ra.com.service.imp.CategoriesServiceImp; // Import lớp implement của CategoriesService từ package ra.com.service.imp.

import javax.servlet.*; // Import các lớp liên quan đến Servlet.
import javax.servlet.http.*; // Import các lớp liên quan đến HttpServlet.
import javax.servlet.annotation.*; // Import các annotation của Servlet.
import java.io.IOException; // Import lớp IOException dùng cho xử lý ngoại lệ I/O.
import java.util.List; // Import lớp List để làm việc với danh sách.

@WebServlet(name = "CategoriesController", value = "/CategoriesController") // Định nghĩa Servlet với URL mapping "/CategoriesController".
//name = "CategoriesController"	Tên định danh của servlet (dùng nội bộ, có thể bỏ qua).
//value = "/CategoriesController"	Đường dẫn (URL pattern) mà servlet này sẽ xử lý.
public class CategoriesController extends HttpServlet { // Khai báo lớp CategoriesController kế thừa HttpServlet.
    // Tiêm một instance của CategoriesService
    private final CategoriesService categoriesService; // Khai báo biến categoriesService kiểu CategoriesService.

    public CategoriesController() { // Constructor của CategoriesController.
        categoriesService = new CategoriesServiceImp(); // Khởi tạo đối tượng CategoriesServiceImp và gán vào biến categoriesService.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy tham số action trong request
        String action = request.getParameter("action"); // Lấy tham số action từ request.

        if (action.equals("findAll")) { // Nếu action là "findAll".
            findAll(request, response); // Gọi phương thức findAll để lấy tất cả danh mục.
        } else if (action.equals("create")) { // Nếu action là "create".
            request.getRequestDispatcher("/views/newCategories.jsp").forward(request, response); // Chuyển hướng đến trang newCategories.jsp.
        } else if (action.equals("initUpdate")) { // Nếu action là "initUpdate".
            int catalogId = Integer.parseInt(request.getParameter("catalogId")); // Lấy catalogId từ request và chuyển sang kiểu int.
            Categories catalog = categoriesService.findById(catalogId); // Gọi service tìm danh mục theo ID.
            request.setAttribute("catalog", catalog); // Đặt đối tượng catalog vào request.
            request.getRequestDispatcher("/views/updateCategories.jsp").forward(request, response); // Chuyển hướng đến trang updateCategories.jsp.
        } else if (action.equals("delete")) { // Nếu action là "delete".
            int catalogId = Integer.parseInt(request.getParameter("catalogId")); // Lấy catalogId từ request và chuyển sang kiểu int.
            boolean result = categoriesService.delete(catalogId); // Gọi service xóa danh mục theo ID và nhận kết quả.
            if (result) { // Nếu xóa thành công.
                findAll(request, response); // Gọi phương thức findAll để cập nhật danh sách.
            } else { // Nếu xóa không thành công.
                request.getRequestDispatcher("/views/error.jsp").forward(request, response); // Chuyển hướng đến trang error.jsp.
            }
        }
    }
//request.getRequestDispatcher() được dùng để chuyển hướng
// (forward) hoặc gộp nội dung (include) giữa các tài nguyên nội bộ
// trong cùng web application, như:
//
//Một file JSP khác,
//
//Một Servlet khác,
//
//Một HTML nội bộ.
//
//Nói cách khác, nó cho phép chuyển request từ servlet hiện tại sang một tài nguyên khác trên server (không phải redirect ra ngoài trình duyệt).
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 2. Gọi phương thức findAll từ service để lấy danh sách danh mục
        List<Categories> listCategories = categoriesService.findAll(); // Lấy danh sách tất cả danh mục từ service.
        // 3. Đặt listCategories vào request để truyền sang view
        request.setAttribute("listCategories", listCategories); // Đặt danh sách vào request.
        // 4. Chuyển request và response sang trang categories.jsp
        request.getRequestDispatcher("/views/categories.jsp").forward(request, response); // Chuyển hướng đến trang categories.jsp.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Thiết lập mã hóa ký tự để xử lý tiếng Việt.
        String action = request.getParameter("action"); // Lấy tham số action từ request.

        if (action.equals("create")) { // Nếu action là "create".
            // Thực hiện thêm mới
            // 1. Lấy dữ liệu từ body của request và tạo đối tượng catalog
            Categories catalog = new Categories(); // Khởi tạo đối tượng Categories mới.
            catalog.setCatalogName(request.getParameter("catalogName")); // Gán tên danh mục từ request vào catalog.
            catalog.setDescription(request.getParameter("description")); // Gán mô tả từ request vào catalog.
            catalog.setStatus(Boolean.parseBoolean(request.getParameter("status"))); // Gán trạng thái từ request vào catalog.

            // 2. Gọi service để lưu catalog vào db và nhận lại kết quả
            boolean result = categoriesService.save(catalog); // Gọi service để lưu catalog và nhận kết quả.

            // 3. Nếu kết quả là true, gọi lại phương thức findAll để hiển thị danh sách
            if (result) { // Nếu lưu thành công.
                findAll(request, response); // Gọi phương thức findAll để cập nhật danh sách.
            } else { // Nếu lưu thất bại.
                request.getRequestDispatcher("/views/error.jsp").forward(request, response); // Chuyển hướng đến trang error.jsp.
            }
        } else if (action.equals("update")) { // Nếu action là "update".
            Categories catalog = new Categories(); // Khởi tạo đối tượng Categories mới.
            catalog.setCatalogId(Integer.parseInt(request.getParameter("catalogId"))); // Gán catalogId từ request vào catalog.
            catalog.setCatalogName(request.getParameter("catalogName")); // Gán tên danh mục từ request vào catalog.
            catalog.setDescription(request.getParameter("description")); // Gán mô tả từ request vào catalog.
            catalog.setStatus(Boolean.parseBoolean(request.getParameter("status"))); // Gán trạng thái từ request vào catalog.

            boolean result = categoriesService.update(catalog); // Gọi service để cập nhật catalog và nhận kết quả.

            if (result) { // Nếu cập nhật thành công.
                findAll(request, response); // Gọi phương thức findAll để cập nhật danh sách.
            } else { // Nếu cập nhật thất bại.
                request.getRequestDispatcher("/views/error.jsp").forward(request, response); // Chuyển hướng đến trang error.jsp.
            }

        }
    }
}
