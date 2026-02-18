package com.example.dao;

import com.example.model.Product;
import java.util.List;

public interface ProductDAO {
    void create(Product product);
    Product read(int id);
    void update(Product product);
    void delete(int id);
    List<Product> getAll();
}