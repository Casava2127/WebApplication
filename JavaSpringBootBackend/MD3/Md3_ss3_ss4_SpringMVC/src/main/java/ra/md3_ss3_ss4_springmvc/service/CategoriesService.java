package ra.md3_ss3_ss4_springmvc.service;

import ra.md3_ss3_ss4_springmvc.entity.Categories;

import java.util.List;

public interface CategoriesService {
    List<Categories> findAll();

    boolean save(Categories catalog);

    Categories findById(int catalogId);

    boolean update(Categories catalog);

    boolean delete(int catalogId);
}
