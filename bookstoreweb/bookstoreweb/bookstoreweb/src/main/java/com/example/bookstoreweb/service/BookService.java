package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.Author;
import com.example.bookstoreweb.entity.Book;
import com.example.bookstoreweb.repository.AuthorRepository;
import com.example.bookstoreweb.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public List<Book> findAllById(List<Long> ids) {
        return bookRepository.findAllById(ids);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            // Clear relations to avoid FK constraint violation
            if (book.getCategories() != null) {
                book.getCategories().clear();
            }
            book.setAuthor(null);

            bookRepository.save(book); // ⚡ Intermediate save is IMPORTANT!
            bookRepository.delete(book);
        }
    }
    public List<Book> searchByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }


    public List<Book> searchAndMerge(String keyword) {
        List<Book> matched = bookRepository.findByTitleContainingIgnoreCase(keyword);
        List<Book> all = bookRepository.findAll();

        // If nothing matched, just return all books
        if (matched.isEmpty()) {
            return all;
        }

        // Otherwise, matched first, then unmatched
        LinkedHashSet<Book> ordered = new LinkedHashSet<>(matched);
        for (Book b : all) {
            ordered.add(b); // set keeps unique by equals+hashCode
        }
        return List.copyOf(ordered);
    }

}
