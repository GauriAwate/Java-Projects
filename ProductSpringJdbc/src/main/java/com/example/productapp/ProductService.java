package com.example.productapp;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDAO dao;

    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public void addProduct(Product product) {
        dao.insert(product);
    }

    public void updateProduct(Product product) {
        dao.update(product);
    }

    public void deleteProduct(int id) {
        dao.delete(id);
    }

    public Product getProduct(int id) {
        return dao.getById(id);
    }

    public List<Product> getAllProducts() {
        return dao.getAll();
    }
}
