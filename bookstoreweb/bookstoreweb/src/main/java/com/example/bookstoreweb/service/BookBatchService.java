package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.Book;
import com.example.bookstoreweb.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookBatchService {

    private final BookRepository bookRepository;

    @Transactional
    public void saveBooksInBatch(List<Book> books) {
        bookRepository.saveAll(books);
    }
}
