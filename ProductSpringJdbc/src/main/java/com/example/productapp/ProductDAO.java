package com.example.productapp;

import java.util.List;

public interface ProductDAO {
    void insert(Product product);
    void update(Product product);
    void delete(int id);
    Product getById(int id);
    List<Product> getAll();
}
