package com.example.bookstoreweb.repository;

import com.example.bookstoreweb.entity.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {

    @Query("SELECT o FROM BookOrder o JOIN FETCH o.books")
    List<BookOrder> findAllWithBooks();
}

