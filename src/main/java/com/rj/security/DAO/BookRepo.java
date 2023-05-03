package com.rj.security.DAO;

import org.springframework.data.repository.CrudRepository;
import com.rj.security.Model.Book;

public interface BookRepo extends CrudRepository<Book, Integer> {
    public Book findById(int id);
}
