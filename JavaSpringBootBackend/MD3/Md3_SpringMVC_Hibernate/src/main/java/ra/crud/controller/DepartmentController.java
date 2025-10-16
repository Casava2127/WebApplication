
package ra.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.crud.model.Department;
import ra.crud.service.DepartmentService;

import java.util.List;

@Controller
@RequestMapping("/departmentController")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/findAll")
    public String findALlDepartment(Model model) {
        List<Department> listDepartments = departmentService.findAll();
        model.addAttribute("listDepartments", listDepartments);
        return "departments";
    }

    @GetMapping("/initUpdate")
    public String initUpdateDepartment(Model model, int deptId) {
        Department deptUpdate = departmentService.findById(deptId);
        model.addAttribute("deptUpdate", deptUpdate);
        return "updateDepartment";
    }

    @PostMapping("/update")
    public String updateDepartment(Department deptUpdate) {
        boolean result = departmentService.update(deptUpdate);
        if (result) {
            return "redirect:findAll";
        }
        return "error";
    }
}
