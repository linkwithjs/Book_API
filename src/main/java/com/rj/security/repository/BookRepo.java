package com.rj.security.repository;

import org.springframework.data.repository.CrudRepository;
import com.rj.security.model.Book;

public interface BookRepo extends CrudRepository<Book, Integer> {
    public Book findById(int id);
}
