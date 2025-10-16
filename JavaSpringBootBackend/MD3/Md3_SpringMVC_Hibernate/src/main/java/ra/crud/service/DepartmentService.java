package ra.crud.service;

import ra.crud.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    Department findById(int deptId);

    boolean save(Department department);

    boolean update(Department department);

    boolean delete(int deptId);
}