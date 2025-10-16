package ra.crud.repository;

import ra.crud.model.Department;

import java.util.List;

public interface DepartmentRepository {
    List<Department> findAll();

    Department findById(int deptId);

    boolean save(Department department);

    boolean update(Department department);

    boolean delete(int deptId);
}