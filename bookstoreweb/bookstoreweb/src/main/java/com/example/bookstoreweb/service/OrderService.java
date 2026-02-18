package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.BookOrder;

import com.example.bookstoreweb.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(BookOrder order) {
        orderRepository.save(order);
    }

    public List<BookOrder> findAll() {
        return orderRepository.findAll();
    }
}
