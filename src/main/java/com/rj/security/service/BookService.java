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

    // adding the book
    public Book addBook(Book b) {
        Book result = bookRepo.save(b);
        return result;
    }
    public List<Book> getBooks(){
        return (List<Book>) bookRepo.findAll();
    }
}
