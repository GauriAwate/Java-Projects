package com.example.dao;

import com.example.model.Student;

import java.util.List;

public interface StudentDAO {
    void create(Student student);
    List<Student> readAll();
    void update(Student student);
    void delete(int id);
}
