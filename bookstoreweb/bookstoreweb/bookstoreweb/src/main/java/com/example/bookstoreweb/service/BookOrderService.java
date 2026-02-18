package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.BookOrder;
import com.example.bookstoreweb.repository.BookOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookOrderService {

    private final BookOrderRepository bookOrderRepository;

    public List<BookOrder> findAll() {
        return bookOrderRepository.findAll();
    }
}
