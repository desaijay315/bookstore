package com.springboot.bookstore.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }

    public static BookNotFoundException forCode(String code) {
        return new BookNotFoundException("Book with code " + code + " not found");
    }
}