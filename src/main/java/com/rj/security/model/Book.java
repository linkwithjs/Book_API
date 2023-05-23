package com.rj.security.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private int id;
    private String bookName;
    private String publisher;
    private Integer quantity;
    private Integer price;

    public Book() {
    }

    public Book(int id, String bookName, String publisher, Integer quantity, Integer price) {
        this.id = id;
        this.bookName = bookName;
        this.publisher = publisher;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", bookName=" + bookName + ", publisher=" + publisher + ", quantity=" + quantity
                + ", price=" + price + "]";
    }

}
