package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.Author;
import com.example.bookstoreweb.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findByName(String name) { return authorRepository.findByName(name); }
    public void save(Author author) { authorRepository.save(author); }
}
