package com.example.dao;

import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Student student) {
        String sql = "INSERT INTO students (id, name, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, student.getId(), student.getName(), student.getEmail());
        System.out.println("Student added.");
    }

    @Override
    public List<Student> readAll() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, new RowMapper<Student>() {
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        });
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET name=?, email=? WHERE id=?";
        int rows = jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getId());
        if (rows > 0) System.out.println("Student updated.");
        else System.out.println("Student not found.");
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) System.out.println("Student deleted.");
        else System.out.println("Student not found.");
    }
}
