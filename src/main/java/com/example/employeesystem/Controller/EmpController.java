package com.example.employeesystem.Controller;


import com.example.employeesystem.entity.Employee;
import com.example.employeesystem.repository.EmplyeeRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@SessionAttributes({"a","e"})
public class EmpController {

    @Autowired
    private EmplyeeRepository employeeRepository;
    static int num=0;
    @GetMapping(path="/index")
    public String employees(Model model, @RequestParam(name="keyword",defaultValue =
            "") String keyword){
        List<Employee> employees;
        if (keyword.isEmpty()) {
            employees = employeeRepository.findAll();
        } else {
            long key = Long.parseLong(keyword);
            employees = employeeRepository.findStudentById(key);
        }
        model.addAttribute("listStudents", employees);
        return "index";
    }
    @GetMapping("/delete")
    public String delete(Long id){
        employeeRepository.deleteById(id);
        return "redirect:/index";
    }
    @GetMapping("/formEmployee")
    public String formEmployees(Model model){
        model.addAttribute("employee",new Employee());
        return "formEmployees";
    }
    @PostMapping(path="/save")
    public String save(Model model, Employee employee, BindingResult
            bindingResult, ModelMap mm, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "formEmployees";
        } else {
            employeeRepository.save(employee);
            if (num == 2) {
                mm.put("e", 2);
                mm.put("a", 0);
            } else
                mm.put("a", 1);
            mm.put("e", 0);
        }
        return "redirect:index";
    }
    @GetMapping("/editEmployees")
    public String editEmployees(Model model, Long id, HttpSession session){
        num = 2;
        session.setAttribute("info", 0);
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee==null) throw new RuntimeException("Patient does not exist");
        model.addAttribute("employee", employee);
        return "editEmployees";
    }
    @GetMapping(path = "/")
    public String employees2(Model model, ModelMap mm,
                            @RequestParam(name="keyword",defaultValue = "") String
                                    keyword,HttpSession session){
        List<Employee> employees;
        if (keyword.isEmpty()) {
            employees = employeeRepository.findAll();
        } else {
            mm.put("e", 0);
            mm.put("a", 0);
            long key = Long.parseLong(keyword);
            employees = employeeRepository.findStudentById(key);
        }
        model.addAttribute("listStudents", employees);
        return "employees";
    }

}
