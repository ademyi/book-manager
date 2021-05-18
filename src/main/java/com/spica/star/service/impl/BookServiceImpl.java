package com.spica.star.service.impl;

import com.spica.star.model.Book;
import com.spica.star.model.BookLoaned;
import com.spica.star.model.Person;
import com.spica.star.repository.BookRepository;
import com.spica.star.service.BookService;
import com.spica.star.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PersonService personService;

    @Autowired
    public BookServiceImpl(BookRepository repository, PersonService personService) {
        this.bookRepository = repository;
        this.personService = personService;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(long id, Book book) {
        Book result = bookRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")); // TODO maybe ControllerAdvice

        result.setTitle(book.getTitle());
        result.setAuthor(book.getAuthor());
        result.setLoanedList(book.getLoanedList());

        return bookRepository.save(result);
    }

    @Override
    public void deleteBook(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")); // TODO maybe ControllerAdvice

        bookRepository.delete(book);
    }

    @Override
    public void LendBook(long bookId, long personId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        Person person = personService.getPersonById(personId);

        boolean isLoaned =   isLoanedBook(book);                // check the availability of the book

        if (!isLoaned){
            book.loanBook(person);

            bookRepository.save(book);
            personService.updatePerson(person.getId(), person);
        }

    }

    @Override
    public void returnBook(long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        Optional<BookLoaned> loaned = book.getLoanedList().stream()
                .filter( p -> p.getBook().getId() == bookId)
                .findFirst();

        if (loaned.isPresent()){
            book.returnBook( loaned.get().getPerson() );

            bookRepository.save(book);
        }
    }

    private boolean isLoanedBook(Book book) {
        return book.getLoanedList().stream()
                .anyMatch( b -> b.getBook().getId() == book.getId() );
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> result = bookRepository.findById(id);
        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

    }

    @Override
    public Book getBookByName(String name){
        Optional<Book> result = bookRepository.findBookByTitleContains(name);
        return result.orElse(null);
    }

}
