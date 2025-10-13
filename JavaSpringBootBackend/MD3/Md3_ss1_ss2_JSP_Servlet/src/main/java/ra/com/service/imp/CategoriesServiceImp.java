package ra.com.service.imp; // Khai báo package chứa lớp CategoriesServiceImp.

import ra.com.entity.Categories; // Import lớp Categories từ package ra.com.entity.
import ra.com.reposistory.CategoriesRepository; // Import interface CategoriesRepository từ package ra.com.reposistory.
import ra.com.reposistory.imp.CategoriesRepositoryImp; // Import lớp implement của CategoriesRepository từ package ra.com.reposistory.imp.
import ra.com.service.CategoriesService; // Import interface CategoriesService từ package ra.com.service.

import java.util.List; // Import lớp List để sử dụng danh sách.

public class CategoriesServiceImp implements CategoriesService { // Khai báo lớp CategoriesServiceImp implement interface CategoriesService.
    // Tiêm vào một instance của CategoriesRepository
    private final CategoriesRepository categoriesRepository; // Khai báo biến categoriesRepository kiểu CategoriesRepository.

    public CategoriesServiceImp() { // Constructor của CategoriesServiceImp.
        categoriesRepository = new CategoriesRepositoryImp(); // Khởi tạo đối tượng CategoriesRepositoryImp và gán vào biến categoriesRepository.
    }

    @Override
    public List<Categories> findAll() { // Ghi đè phương thức findAll từ CategoriesService.
        return categoriesRepository.findAll(); // Gọi phương thức findAll của categoriesRepository để lấy danh sách danh mục.
    }

    @Override
    public boolean save(Categories catalog) { // Ghi đè phương thức save từ CategoriesService.
        return categoriesRepository.save(catalog); // Gọi phương thức save của categoriesRepository để lưu danh mục vào cơ sở dữ liệu.
    }

    @Override
    public Categories findById(int catalogId) { // Ghi đè phương thức findById từ CategoriesService.
        return categoriesRepository.findById(catalogId); // Gọi phương thức findById của categoriesRepository để tìm danh mục theo ID.
    }

    @Override
    public boolean update(Categories catalog) { // Ghi đè phương thức update từ CategoriesService.
        return categoriesRepository.update(catalog); // Gọi phương thức update của categoriesRepository để cập nhật danh mục trong cơ sở dữ liệu.
    }

    @Override
    public boolean delete(int catalogId) { // Ghi đè phương thức delete từ CategoriesService.
        return categoriesRepository.delete(catalogId); // Gọi phương thức delete của categoriesRepository để xóa danh mục theo ID.
    }
}
