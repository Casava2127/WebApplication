package ra.com.reposistory;

import ra.com.entity.Categories;

import java.util.List;

public interface CategoriesRepository {
    List<Categories> findAll();

    boolean save(Categories catalog);

    Categories findById(int catalogId);

    boolean update(Categories catalog);

    boolean delete(int catalogId);
}
