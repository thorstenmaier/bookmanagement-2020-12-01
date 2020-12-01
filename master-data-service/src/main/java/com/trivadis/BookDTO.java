package com.trivadis;

import java.util.Objects;

public class BookDTO {

    private String name;
    private String author;

    public BookDTO(Book book) {
        this.name = book.getName();
        this.author = book.getAuthor().getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
