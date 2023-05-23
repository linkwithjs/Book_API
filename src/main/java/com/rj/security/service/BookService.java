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
    // get single book by id
    public Book getBookById(int id) {
        Book book = null;
        try {
            book = this.bookRepo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    // adding the book
    public Book addBook(Book b) {
        Book result = bookRepo.save(b);
        return result;
    }

    // Delete a book
    public void deleteBook(int bid) {
        bookRepo.deleteById(bid);
    }


    // Update book
    public Book updateBook(Book book, int bookId) {
        Book result = bookRepo.findById(bookId);
        try {
            if (result != null) {
                book.setId(bookId);
                bookRepo.save(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
