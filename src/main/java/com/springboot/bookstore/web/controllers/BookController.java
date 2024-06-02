package com.springboot.bookstore.web.controllers;

import com.springboot.bookstore.domain.Book;
import com.springboot.bookstore.domain.BookNotFoundException;
import com.springboot.bookstore.domain.BookService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * GET /api/books/{code} : Get a book by its code.
     *
     * @param code the code of the book to retrieve
     * @return the book
     */
    @GetMapping("/{code}")
    public ResponseEntity<Book> getBookByCode(@PathVariable String code) {
        log.info("Fetching book for code: {}", code);
        return bookService.getBookByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> BookNotFoundException.forCode(code));
    }

    /**
     * POST /api/books : Create a new book.
     *
     * @param book the book to create
     * @return the created book
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        log.info("Creating book: {}", book);
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }


    /**
     * PUT /api/books/{code} : Update an existing book.
     *
     * @param code the code of the book to update
     * @param updatedBook the updated book data
     * @return the updated book
     */
    @PutMapping("/{code}")
    public ResponseEntity<Book> updateBook(@PathVariable String code, @RequestBody Book updatedBook) {
        log.info("Updating book with code: {}", code);
        Optional<Book> updated = bookService.updateBook(code, updatedBook);
        return updated.map(ResponseEntity::ok)
                .orElseThrow(() -> BookNotFoundException.forCode(code));
    }

    /**
     * DELETE /api/books/{code} : Delete a book by its code.
     *
     * @param code the code of the book to delete
     * @return the response entity
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteBook(@PathVariable String code) {
        log.info("Deleting book with code: {}", code);
        bookService.deleteBook(code);
        return ResponseEntity.noContent().build();
    }

}
