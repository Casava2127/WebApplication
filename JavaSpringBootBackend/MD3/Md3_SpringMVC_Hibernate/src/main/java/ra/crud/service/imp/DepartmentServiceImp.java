
package ra.crud.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.crud.model.Department;
import ra.crud.repository.DepartmentRepository;
import ra.crud.service.DepartmentService;

import java.util.List;

@Service
public class DepartmentServiceImp implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(int deptId) {
        return departmentRepository.findById(deptId);
    }

    @Override
    public boolean save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public boolean update(Department department) {
        return departmentRepository.update(department);
    }

    @Override
    public boolean delete(int deptId) {
        return departmentRepository.delete(deptId);
    }
}
