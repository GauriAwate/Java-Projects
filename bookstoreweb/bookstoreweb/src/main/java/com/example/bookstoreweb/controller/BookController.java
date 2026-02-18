package com.example.bookstoreweb.controller;

import com.example.bookstoreweb.entity.*;
import com.example.bookstoreweb.repository.BookOrderRepository;
import com.example.bookstoreweb.repository.BookRepository;
import com.example.bookstoreweb.repository.ReviewRepository;
import com.example.bookstoreweb.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final ReviewRepository reviewRepo;
    private final BookService bookService;
    private final ReviewService reviewService;
    private final AuthorService authorService;
    private final OrderService orderService;
    private final BookBatchService bookBatchService;
    private final BookOrderRepository  bookOrderRepository;
    private final BookRepository bookRepo;
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        List<Book> books = bookService.findAll();

        // Build author -> book count map
        Map<Long, Long> authorBookCounts = new HashMap<>();
        for (Book book : books) {
            if (book.getAuthor() != null) {
                Long authorId = book.getAuthor().getId();
                authorBookCounts.put(authorId,
                        authorBookCounts.getOrDefault(authorId, 0L) + 1);
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("authorBookCounts", authorBookCounts);

        return "admin/dashboard";
    }


    @GetMapping("/admin/book/new")
    public String newBook(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    @PostMapping("/admin/book/save")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam String authorName,
                           HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        Author author = authorService.findByName(authorName);
        if (author == null) {
            author = new Author();
            author.setName(authorName);
            authorService.save(author);
        }

        book.setAuthor(author);
        bookService.save(book);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/book/edit/{id}")
    public String editBook(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("book", bookService.findById(id));
        return "admin/book-form";
    }

    @GetMapping("/admin/book/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        bookService.deleteBookById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/books")
    public String browseBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            HttpSession session,
            Model model
    ) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Book> books = bookService.findAll();

        if (keyword != null && !keyword.trim().isEmpty()) {
            books = bookService.searchAndMerge(keyword);
        }

        // Filter by author
        if (author != null && !author.isBlank()) {
            books = books.stream()
                    .filter(b -> b.getAuthor() != null && b.getAuthor().getName().toLowerCase().contains(author.toLowerCase()))
                    .toList();
        }

        // Filter by price
        if (minPrice != null) {
            books = books.stream()
                    .filter(b -> b.getPrice() >= minPrice)
                    .toList();
        }
        if (maxPrice != null) {
            books = books.stream()
                    .filter(b -> b.getPrice() <= maxPrice)
                    .toList();
        }

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("author", author);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "user/browse-books";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        Book book = bookService.findById(id);
        if (book != null) {
            List<Book> cart = (List<Book>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }
            cart.add(book);
            session.setAttribute("cart", cart);
        }
        return "redirect:/books?added=true";
    }

    @PostMapping("/books/buy/{id}")
    public String buyNow(@PathVariable Long id, HttpSession session) {
        Book book = bookService.findById(id);
        if (book != null) {
            List<Book> orderBooks = new ArrayList<>();
            orderBooks.add(book);
            session.setAttribute("order", orderBooks);
        }
        return "redirect:/confirm-order";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Book> cart = (List<Book>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        double total = cart.stream().mapToDouble(Book::getPrice).sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "user/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkoutCart(HttpSession session) {
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            session.setAttribute("order", new ArrayList<>(cart));
        }
        return "redirect:/confirm-order";
    }

    @GetMapping("/confirm-order")
    public String confirmOrder(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Book> orderBooks = (List<Book>) session.getAttribute("order");
        if (orderBooks == null) {
            orderBooks = new ArrayList<>();
        }
        double total = orderBooks.stream().mapToDouble(Book::getPrice).sum();

        model.addAttribute("order", orderBooks);
        model.addAttribute("total", total);
        return "confirm-order";
    }

    @PostMapping("/confirm-order")
    public String placeOrder(HttpSession session) {
        List<Book> orderBooks = (List<Book>) session.getAttribute("order");
        User user = (User) session.getAttribute("user");

        if (user != null && orderBooks != null && !orderBooks.isEmpty()) {
            // Get managed Book entities:
            List<Long> bookIds = orderBooks.stream().map(Book::getId).toList();
            List<Book> attachedBooks = bookService.findAllById(bookIds);

            // Create order
            BookOrder order = new BookOrder();
            order.setUsername(user.getUsername());
            order.setStatus("PLACED");

            // First save parent ➜ gets ID
            orderService.save(order);

            // Now attach books
            order.setBooks(attachedBooks);
            orderService.save(order); // update join table
        }

        session.removeAttribute("order");
        session.removeAttribute("cart");
        return "order-success";
    }

    @GetMapping("/admin/book/batch-upload")
    public String showBatchUpload() {
        return "admin/batch-upload";
    }

    @PostMapping("/admin/batch-save")
    public String batchSave(
            @RequestParam("titles") List<String> titles,
            @RequestParam("prices") List<Double> prices,
            @RequestParam("authors") List<String> authors,
            Model model
    ) {
        for (int i = 0; i < titles.size(); i++) {
            Book book = new Book();
            book.setTitle(titles.get(i));
            book.setPrice(prices.get(i));

            Author author = authorService.findByName(authors.get(i));
            if (author == null) {
                author = new Author();
                author.setName(authors.get(i));
                authorService.save(author);
            }

            book.setAuthor(author);
            bookService.save(book);
        }

        model.addAttribute("message", "Books saved successfully!");
        return "admin/batch-upload";
    }


    @GetMapping("/admin/orders")
    public String viewOrders(Model model) {
        List<BookOrder> orders = bookOrderRepository.findAllWithBooks();

        Map<Long, Double> orderTotals = new HashMap<>();
        for (BookOrder order : orders) {
            double total = order.getBooks().stream().mapToDouble(Book::getPrice).sum();
            orderTotals.put(order.getId(), total);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("orderTotals", orderTotals);
        return "admin/orders";
    }


    @GetMapping("/books/details/{id}")
    public String viewBookDetails(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "user/book-details";
    }
    @PostMapping("/books/{bookId}/reviews")
    public String submitReview(@PathVariable Long bookId,
                               @RequestParam("comment") String comment,
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }

        Book book = bookService.findBookById(bookId);
        if (book == null) {
            return "redirect:/books"; // If book doesn't exist, redirect to list
        }

        reviewService.addReview(comment, book, user);
        return "redirect:/book-details"; // Redirect to book details to show the review
    }

    @PostMapping("/user/books/{bookId}/reviews")
    public String addReview(@PathVariable Long bookId,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";

        Book book = bookRepo.findById(bookId).orElseThrow();
        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepo.save(review);
        return "redirect:user/book-details" + bookId;
    }
}
