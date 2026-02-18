package com.example.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "<h1>Hello from Spring Controller! (USER access)</h1>";
    }
    @GetMapping("/admin")
    @ResponseBody
    public String adminPage() {
        return "<h1>Welcome to Admin Page! (ADMIN access)</h1>";
    }
    @GetMapping("/public")
    @ResponseBody
    public String publicPage() {
        return "<h1>This is a Public Page! (No authentication required)</h1>";
    }
}