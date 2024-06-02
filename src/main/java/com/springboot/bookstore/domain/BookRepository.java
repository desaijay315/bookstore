package com.springboot.bookstore.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BookEntity} entities.
 */

interface BookRepository extends JpaRepository<BookEntity, Long> {

    /**
     * Find a book by its code.
     *
     * @param code the code of the book
     * @return an Optional containing the found book or empty if no book found
     */
    Optional<BookEntity> findByCode(String code);
}
