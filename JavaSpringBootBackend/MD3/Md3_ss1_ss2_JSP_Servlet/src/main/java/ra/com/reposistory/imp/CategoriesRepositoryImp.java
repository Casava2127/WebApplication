package ra.com.reposistory.imp; // Khai báo package chứa lớp CategoriesRepositoryImp.

import ra.com.entity.Categories; // Import lớp Categories từ package ra.com.entity.
import ra.com.reposistory.CategoriesRepository; // Import interface CategoriesRepository từ package ra.com.reposistory.
import ra.com.util.ConnectionDB; // Import lớp ConnectionDB để quản lý kết nối cơ sở dữ liệu.

import java.sql.CallableStatement; // Import lớp CallableStatement để gọi các stored procedure.
import java.sql.Connection; // Import lớp Connection để tạo kết nối tới cơ sở dữ liệu.
import java.sql.ResultSet; // Import lớp ResultSet để xử lý kết quả trả về từ cơ sở dữ liệu.
import java.util.ArrayList; // Import lớp ArrayList để tạo danh sách động.
import java.util.List; // Import interface List để làm việc với danh sách.

public class CategoriesRepositoryImp implements CategoriesRepository { // Khai báo lớp CategoriesRepositoryImp implement interface CategoriesRepository.

    @Override
    public List<Categories> findAll() { // Ghi đè phương thức findAll từ CategoriesRepository.
        Connection conn = null; // Khởi tạo biến conn kiểu Connection để lưu kết nối cơ sở dữ liệu.
        CallableStatement callSt = null; // Khởi tạo biến callSt kiểu CallableStatement để gọi stored procedure.
        List<Categories> listCategories = null; // Khởi tạo biến listCategories để lưu danh sách các danh mục.

        try {
            conn = ConnectionDB.openConnection(); // Mở kết nối cơ sở dữ liệu bằng phương thức openConnection của ConnectionDB.
            callSt = conn.prepareCall("{call find_all_categories()}"); // Chuẩn bị callable statement cho stored procedure `find_all_categories`.
            ResultSet rs = callSt.executeQuery(); // Thực thi câu lệnh và lưu kết quả trả về vào ResultSet rs.
            listCategories = new ArrayList<>(); // Khởi tạo listCategories là một danh sách rỗng.

            while (rs.next()) { // Lặp qua từng dòng kết quả trong rs.
                Categories categories = new Categories(); // Khởi tạo đối tượng Categories mới.
                categories.setCatalogId(rs.getInt("catalog_id")); // Lấy giá trị "catalog_id" từ rs và đặt vào đối tượng categories.
                categories.setCatalogName(rs.getString("catalog_name")); // Lấy giá trị "catalog_name" từ rs và đặt vào đối tượng categories.
                categories.setDescription(rs.getString("catalog_description")); // Lấy giá trị "catalog_description" từ rs và đặt vào đối tượng categories.
                categories.setStatus(rs.getBoolean("catalog_status")); // Lấy giá trị "catalog_status" từ rs và đặt vào đối tượng categories.
                listCategories.add(categories); // Thêm đối tượng categories vào listCategories.
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra thông báo lỗi nếu xảy ra ngoại lệ.
        } finally {
            ConnectionDB.closeConnection(conn, callSt); // Đóng kết nối cơ sở dữ liệu và giải phóng tài nguyên.
        }
        return listCategories; // Trả về danh sách các danh mục.
    }

    @Override
    public boolean save(Categories catalog) { // Ghi đè phương thức save từ CategoriesRepository.
        Connection conn = null; // Khởi tạo biến conn kiểu Connection.
        CallableStatement callSt = null; // Khởi tạo biến callSt kiểu CallableStatement.
        boolean result = false; // Khởi tạo biến result để lưu kết quả.

        try {
            conn = ConnectionDB.openConnection(); // Mở kết nối cơ sở dữ liệu.
            callSt = conn.prepareCall("{call create_new_catalog(?,?,?)}"); // Chuẩn bị callable statement cho stored procedure `create_new_catalog`.
            callSt.setString(1, catalog.getCatalogName()); // Đặt giá trị tên danh mục từ catalog vào tham số 1.
            callSt.setString(2, catalog.getDescription()); // Đặt giá trị mô tả từ catalog vào tham số 2.
            callSt.setBoolean(3, catalog.isStatus()); // Đặt trạng thái từ catalog vào tham số 3.
            callSt.executeUpdate(); // Thực thi câu lệnh để lưu danh mục vào cơ sở dữ liệu.
            result = true; // Đặt result là true nếu lưu thành công.
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra thông báo lỗi nếu có ngoại lệ.
        } finally {
            ConnectionDB.closeConnection(conn, callSt); // Đóng kết nối cơ sở dữ liệu và giải phóng tài nguyên.
        }
        return result; // Trả về kết quả lưu danh mục.
    }

    @Override
    public Categories findById(int catalogId) { // Ghi đè phương thức findById từ CategoriesRepository.
        Connection conn = null; // Khởi tạo biến conn kiểu Connection.
        CallableStatement callSt = null; // Khởi tạo biến callSt kiểu CallableStatement.
        Categories catalog = null; // Khởi tạo biến catalog để lưu danh mục tìm được.

        try {
            conn = ConnectionDB.openConnection(); // Mở kết nối cơ sở dữ liệu.
            callSt = conn.prepareCall("{call find_catalog_by_id(?)}"); // Chuẩn bị callable statement cho stored procedure `find_catalog_by_id`.
            callSt.setInt(1, catalogId); // Đặt giá trị catalogId vào tham số 1.
            ResultSet rs = callSt.executeQuery(); // Thực thi câu lệnh và lấy kết quả trả về từ ResultSet rs.
            catalog = new Categories(); // Khởi tạo đối tượng Categories.

            if (rs.next()) { // Nếu có kết quả từ rs.
                catalog.setCatalogId(rs.getInt("catalog_id")); // Lấy "catalog_id" từ rs và gán cho catalog.
                catalog.setCatalogName(rs.getString("catalog_name")); // Lấy "catalog_name" từ rs và gán cho catalog.
                catalog.setDescription(rs.getString("catalog_description")); // Lấy "catalog_description" từ rs và gán cho catalog.
                catalog.setStatus(rs.getBoolean("catalog_status")); // Lấy "catalog_status" từ rs và gán cho catalog.
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra thông báo lỗi nếu có ngoại lệ.
        } finally {
            ConnectionDB.closeConnection(conn, callSt); // Đóng kết nối và giải phóng tài nguyên.
        }
        return catalog; // Trả về danh mục tìm thấy.
    }

    @Override
    public boolean update(Categories catalog) { // Ghi đè phương thức update từ CategoriesRepository.
        Connection conn = null; // Khởi tạo biến conn kiểu Connection.
        CallableStatement callSt = null; // Khởi tạo biến callSt kiểu CallableStatement.
        boolean result = false; // Khởi tạo biến result để lưu kết quả.

        try {
            conn = ConnectionDB.openConnection(); // Mở kết nối cơ sở dữ liệu.
            callSt = conn.prepareCall("{call update_catalog(?,?,?,?)}"); // Chuẩn bị callable statement cho stored procedure `update_catalog`.
            callSt.setInt(1, catalog.getCatalogId()); // Đặt catalogId vào tham số 1.
            callSt.setString(2, catalog.getCatalogName()); // Đặt tên danh mục vào tham số 2.
            callSt.setString(3, catalog.getDescription()); // Đặt mô tả vào tham số 3.
            callSt.setBoolean(4, catalog.isStatus()); // Đặt trạng thái vào tham số 4.
            callSt.executeUpdate(); // Thực thi câu lệnh để cập nhật danh mục.
            result = true; // Đặt result là true nếu cập nhật thành công.
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra thông báo lỗi nếu có ngoại lệ.
        } finally {
            ConnectionDB.closeConnection(conn, callSt); // Đóng kết nối và giải phóng tài nguyên.
        }
        return result; // Trả về kết quả cập nhật.
    }

    @Override
    public boolean delete(int catalogId) { // Ghi đè phương thức delete từ CategoriesRepository.
        Connection conn = null; // Khởi tạo biến conn kiểu Connection.
        CallableStatement callSt = null; // Khởi tạo biến callSt kiểu CallableStatement.
        boolean result = false; // Khởi tạo biến result để lưu kết quả.

        try {
            conn = ConnectionDB.openConnection(); // Mở kết nối cơ sở dữ liệu.
            callSt = conn.prepareCall("{call delete_catalog(?)}"); // Chuẩn bị callable statement cho stored procedure `delete_catalog`.
            callSt.setInt(1, catalogId); // Đặt catalogId vào tham số 1.
            callSt.executeUpdate(); // Thực thi câu lệnh để xóa danh mục.
            result = true; // Đặt result là true nếu xóa thành công.
        } catch (Exception ex) {
            ex.printStackTrace(); // In ra thông báo lỗi nếu có ngoại lệ.
        } finally {
            ConnectionDB.closeConnection(conn, callSt); // Đóng kết nối và giải phóng tài nguyên.
        }
        return result; // Trả về kết quả xóa danh mục.
    }
}
