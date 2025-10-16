package ra.crud.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Department")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "dept_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deptId;
    @Column(name = "dept_name",columnDefinition = "varchar(100)", unique = true, nullable = false)
    private String deptName;
    @Column(name = "dept_description", columnDefinition = "text")
    private String description;
    @Column(name = "dept_status")
    private boolean status;
    @OneToMany(mappedBy = "department")
    private List<Employee> listEmloyees = new ArrayList<>();
}