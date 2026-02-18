package com.example.productapp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Product product) {
        String sql = "INSERT INTO products(name, price, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getQuantity());
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name=?, price=?, quantity=? WHERE id=?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getQuantity(), product.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    private static class ProductMapper implements RowMapper<Product> {
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            );
        }
    }
}
