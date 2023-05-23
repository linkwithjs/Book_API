package com.rj.security.service;

import com.rj.security.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rj.security.repository.BookRepo;
import java.util.List;
@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    // get all books
    public List<Book> getAllBooks() {
        List<Book> list = (List<Book>) this.bookRepo.findAll();
        // System.out.println("books>> " + list);
        return list;
    }

    // adding the book
    public Book addBook(Book b) {
        Book result = bookRepo.save(b);
        return result;
    }

}
