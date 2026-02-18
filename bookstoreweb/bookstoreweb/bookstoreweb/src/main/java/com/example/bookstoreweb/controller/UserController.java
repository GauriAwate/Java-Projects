package com.example.bookstoreweb.controller;

import com.example.bookstoreweb.entity.User;
import com.example.bookstoreweb.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        if (userService.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already registered");
            return "signup";
        }

        userService.registerUser(new User(username, email, password));
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        User user = userService.findByEmail(email).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);  // ✅ STORE USER IN SESSION!
            return "redirect:/books";            // ✅ REDIRECT TO BROWSE BOOKS!
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/admin/login")
    public String showAdminLoginForm() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session,
                             Model model) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            session.setAttribute("admin", username);  // ✅ STORE ADMIN IN SESSION!
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid admin credentials");
            return "admin-login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
