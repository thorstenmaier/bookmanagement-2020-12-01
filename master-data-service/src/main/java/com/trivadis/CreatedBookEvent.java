package com.trivadis;

import java.io.Serializable;

public class CreatedBookEvent implements Serializable {

    private String name;
    private String author;

    public CreatedBookEvent() {
    }

    public CreatedBookEvent(String name, String author) {
        this.name = name;
        this.author = author;
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
