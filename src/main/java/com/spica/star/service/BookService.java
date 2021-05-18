package com.spica.star.service;

import com.spica.star.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBooks();
    Book createBook(Book book);
    Book updateBook(long id, Book book);
    void deleteBook(long id);
    Book getBookById(long id);
    Book getBookByName(String name);
    void LendBook(long bookId, long personId);
    void returnBook(long bookId);
}
