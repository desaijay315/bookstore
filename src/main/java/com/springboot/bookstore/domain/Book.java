package com.springboot.bookstore.domain;
import java.math.BigDecimal;

public record Book(String code, String name, String description, String imageUrl, BigDecimal price) {}
