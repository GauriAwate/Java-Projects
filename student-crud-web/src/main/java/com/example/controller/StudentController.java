package com.example.controller;

import com.example.annotation.Loggable;
import com.example.dao.StudentDAO;
import com.example.model.Student;
import com.example.util.LogViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentDAO studentDAO;
    private final LogViewer logViewer;

    @Autowired
    public StudentController(StudentDAO studentDAO, LogViewer logViewer) {
        this.studentDAO = studentDAO;
        this.logViewer = logViewer;
    }

    @Loggable
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentDAO.getAll();
        model.addAttribute("students", students);
        return "list";
    }

    @Loggable
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "create";
    }

    @Loggable
    @PostMapping("/create")
    public String createStudent(@ModelAttribute("student") Student student) {
        studentDAO.create(student);
        return "redirect:/students";
    }

    @Loggable
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable("id") int id, Model model) {
        Student student = studentDAO.read(id);
        if (student == null) {
            model.addAttribute("error", "Student not found");
            return "view";
        }
        model.addAttribute("student", student);
        return "view";
    }

    @Loggable
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Student student = studentDAO.read(id);
        if (student == null) {
            model.addAttribute("error", "Student not found");
            return "edit";
        }
        model.addAttribute("student", student);
        return "edit";
    }

    @Loggable
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") int id, @ModelAttribute("student") Student student) {
        student.setId(id);
        studentDAO.update(student);
        return "redirect:/students";
    }

    @Loggable
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        studentDAO.delete(id);
        return "redirect:/students";
    }

    @Loggable
    @GetMapping("/logs")
    public String viewLogs(Model model) {
        try {
            String logs = logViewer.readLogFile();
            model.addAttribute("logs", logs);
        } catch (Exception e) {
            model.addAttribute("error", "Error reading logs: " + e.getMessage());
        }
        return "logs";
    }
}