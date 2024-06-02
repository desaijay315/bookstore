package com.springboot.bookstore.domain;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    /**
     * Retrieves a book by its code.
     *
     * @param code the code of the book to retrieve
     * @return an Optional containing the found book or empty if no book found
     */
    public Optional<Book> getBookByCode(String code) {
        return bookRepository.findByCode(code).map(BookMapper::toBook);
    }

    /**
     * Creates a new book.
     *
     * @param book the book to create
     * @return the created book
     */
    public Book createBook(Book book) {
        BookEntity bookEntity = BookMapper.toBookEntity(book);
        bookEntity = bookRepository.save(bookEntity);
        return BookMapper.toBook(bookEntity);
    }

    /**
     * Updates an existing book.
     *
     * @param code the code of the book to update
     * @param updatedBook the updated book data
     * @return an Optional containing the updated book or empty if no book found
     */
    public Optional<Book> updateBook(String code, Book updatedBook) {
        return bookRepository.findByCode(code).map(existingBookEntity -> {
            existingBookEntity.setName(updatedBook.name());
            existingBookEntity.setDescription(updatedBook.description());
            existingBookEntity.setImageUrl(updatedBook.imageUrl());
            existingBookEntity.setPrice(updatedBook.price());
            bookRepository.save(existingBookEntity);
            return BookMapper.toBook(existingBookEntity);
        });
    }

    /**
     * Deletes a book by its code.
     *
     * @param code the code of the book to delete
     */
    public void deleteBook(String code) {
        bookRepository.findByCode(code).ifPresent(bookRepository::delete);
    }
}
