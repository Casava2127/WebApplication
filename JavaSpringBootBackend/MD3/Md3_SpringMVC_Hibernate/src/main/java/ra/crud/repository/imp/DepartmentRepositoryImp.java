package ra.crud.repository.imp;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.crud.model.Department;
import ra.crud.repository.DepartmentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DepartmentRepositoryImp implements DepartmentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Department> findAll() {
        return entityManager.createQuery("from Department", Department.class).getResultList();
    }

    @Override
    public Department findById(int deptId) {
        return entityManager.createQuery("from Department where deptId=:id", Department.class)
                .setParameter("id", deptId).getSingleResult();
    }

    @Override
    @Transactional
    public boolean save(Department department) {
        try {
            entityManager.persist(department);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(Department department) {
        try {
            entityManager.merge(department);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(int deptId) {
        try {
            //1. Lay doi tuong can xoa theo deptId
            Department department = findById(deptId);
            //2. Thuc hien xoa
            entityManager.remove(department);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}