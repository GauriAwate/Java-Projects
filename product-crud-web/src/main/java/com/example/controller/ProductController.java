package com.example.controller;

import com.example.annotation.Loggable;
import com.example.dao.ProductDAO;
import com.example.model.Product;
import com.example.util.LogViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class ProductController {
    private final ProductDAO productDAO;
    private final LogViewer logViewer;
    private static final Logger logger =
            LoggerFactory.getLogger(ProductController.class);
    @Autowired
    public ProductController(ProductDAO productDAO, LogViewer logViewer) {
        this.productDAO = productDAO;
        this.logViewer = logViewer;
    }
    @GetMapping("/")
    public String test(Model model) {
        logger.debug("index endpoint called");
        return "index";
    }
    @Loggable
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productDAO.getAll();
        model.addAttribute("products", products);
        return "list";
    }

    @Loggable
    @GetMapping("/products/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @Loggable
    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute("product") Product product) {
        productDAO.create(product);
        return "redirect:/products";
    }

    @Loggable
    @GetMapping("/products/{id}")
    public String viewProduct(@PathVariable("id") int id, Model model) {
        Product product = productDAO.read(id);
        if (product == null) {
            model.addAttribute("error", "Product not found");
            return "view";
        }
        model.addAttribute("product", product);
        return "view";
    }

    @Loggable
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productDAO.read(id);
        if (product == null) {
            model.addAttribute("error", "Product not found");
            return "edit";
        }
        model.addAttribute("product", product);
        return "edit";
    }

    @Loggable
    @PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") Product product) {
        product.setId(id);
        productDAO.update(product);
        return "redirect:/products";
    }

    @Loggable
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productDAO.delete(id);
        return "redirect:/products";
    }

    @Loggable
    @GetMapping("/products/logs")
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