
package ra.crud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @Column(name = "emp_id", columnDefinition = "char(5)")
    private String empId;
    @Column(name = "emp_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String empName;
    @Column(name = "emp_bod")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bod;
    @Column(name = "emp_salary", columnDefinition = "float check(emp_salary>0)", nullable = false)
    private float salary;
    @Column(name = "emp_status")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "dept_id")
    private Department department;
}
