package com.springboot.bookstore.domain;

import lombok.experimental.UtilityClass;

/**
 * Utility class for mapping between {@link BookEntity} and {@link Book} objects.
 */
@UtilityClass
class BookMapper {
    static Book toBook(BookEntity bookEntity) {
        return new Book(bookEntity.getCode(), bookEntity.getName(), bookEntity.getDescription(),
                bookEntity.getImageUrl(), bookEntity.getPrice());
    }


    /**
     * Converts a {@link Book} to a {@link BookEntity}.
     *
     * @param book the book to convert
     * @return the converted entity
     */
    static BookEntity toBookEntity(Book book) {
        return BookEntity.builder()
                .code(book.code())
                .name(book.name())
                .description(book.description())
                .imageUrl(book.imageUrl())
                .price(book.price())
                .build();
    }
}
