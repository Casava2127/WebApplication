package ra.md3_ss3_ss4_springmvc.repository;

import ra.md3_ss3_ss4_springmvc.entity.Categories;

import java.util.List;

public interface CategoriesRepository {
    List<Categories> findAll();

    boolean save(Categories catalog);

    Categories findById(int catalogId);

    boolean update(Categories catalog);

    boolean delete(int catalogId);
}
