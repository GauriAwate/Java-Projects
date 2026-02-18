package com.example.bookstoreweb.repository;

import com.example.bookstoreweb.entity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<BookOrder, Long> {
}
