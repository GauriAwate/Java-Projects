package com.example.controller;

import com.example.entity.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    // ✅ Constructor injection — recommended over field injection
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ✅ List all employees
    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";
    }

    // ✅ Show empty form for adding a new employee
    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    // ✅ Handle form submission for saving employee
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    // ✅ Show form for updating an existing employee
    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("employeeId") int id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            // Optional: handle not found gracefully
            return "redirect:/employee/list";
        }
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    // ✅ Delete employee by ID
    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee/list";
    }
}
