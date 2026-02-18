package com.example.controller;
import com.example.annotation.Loggable;
import com.example.dao.StudentDAO;
import com.example.model.Student;
import com.example.util.LogViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    private static final Logger logger =
            LoggerFactory.getLogger(StudentController.class);
    private final StudentDAO studentDAO;
    private final LogViewer logViewer;

    @Autowired
    public StudentController(StudentDAO studentDAO, LogViewer
            logViewer) {
        this.studentDAO = studentDAO;
        this.logViewer = logViewer;
    }

    @GetMapping("/")
    public String test(Model model) {
        logger.debug("index endpoint called");
        return "index";
    }

    @Loggable
    @GetMapping("/students")
    public String listStudents(Model model) {
        logger.debug("Listing students");
        List<Student> students = studentDAO.getAll();
        model.addAttribute("students", students);
        return "list";
    }

    @Loggable
    @GetMapping("/students/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "create";
    }

    @Loggable
    @PostMapping("/students/create")
    public String createStudent(@Valid @ModelAttribute("student")
                                Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create";
        }
        try {
            studentDAO.create(student);
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating student: " +
                    e.getMessage());
            return "create";
        }
    }

    @Loggable
    @GetMapping("/students/{id}")
    public String viewStudent(@PathVariable("id") int id, Model
            model) {
        Student student = studentDAO.read(id);
        if (student == null) {
            model.addAttribute("error", "Student not found");
        }
        model.addAttribute("student", student);
        return "view";
    }

    @Loggable
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model
            model) {
        Student student = studentDAO.read(id);
        if (student == null) {
            model.addAttribute("error", "Student not found");
            return "edit";
        }
        model.addAttribute("student", student);
        return "edit";
    }

    @Loggable
    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable("id") int id, @Valid
    @ModelAttribute("student") Student student, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "edit";
        }
        student.setId(id);
        try {
            studentDAO.update(student);
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating student: " +
                    e.getMessage());
            return "edit";
        }
    }

    @Loggable
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, Model
            model) {
        try {
            studentDAO.delete(id);
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting student: " +
                    e.getMessage());
            return "list";
        }
    }

    @Loggable
    @GetMapping("/students/logs")
    public String viewLogs(Model model) {
        try {
            String logs = logViewer.readLogFile();
            model.addAttribute("logs", logs);
        } catch (Exception e) {
            model.addAttribute("error", "Error reading logs: " +
                    e.getMessage());
        }
        return "logs";
    }
}