package com.example.dao;

import com.example.annotation.Loggable;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description")
            );
        }
    };

    @Override
    @Loggable
    public void create(Product product) {
        String sql = "INSERT INTO product (name, price, description) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getDescription());
            System.out.println("Product created successfully in DAO.");
        } catch (Exception e) {
            System.err.println("Error creating product: " + e.getMessage());
            throw new RuntimeException("Failed to create product", e);
        }
    }

    @Override
    @Loggable
    public Product read(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, productRowMapper);
        } catch (Exception e) {
            System.err.println("Error reading product: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Loggable
    public void update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, description = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getDescription(), product.getId());
            System.out.println("Product updated successfully in DAO.");
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            throw new RuntimeException("Failed to update product", e);
        }
    }

    @Override
    @Loggable
    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
            System.out.println("Product deleted successfully in DAO.");
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            throw new RuntimeException("Failed to delete product", e);
        }
    }

    @Override
    @Loggable
    public List<Product> getAll() {
        String sql = "SELECT * FROM product";
        try {
            return jdbcTemplate.query(sql, productRowMapper);
        } catch (Exception e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve products", e);
        }
    }
}