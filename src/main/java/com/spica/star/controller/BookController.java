package com.spica.star.controller;

import com.spica.star.dto.BookDto;
import com.spica.star.dto.LoanDto;
import com.spica.star.model.Book;
import com.spica.star.model.Person;
import com.spica.star.service.BookService;
import com.spica.star.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService, ModelMapper modelMapper){
        super();
        this.bookService = bookService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<BookDto> getAllBooks(){
        return bookService.getAllBooks()
                .stream()
                .map( book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(name = "id") Long id) {
        Book book = bookService.getBookById(id);

        // convert entity to DTO
        BookDto bookResponse = modelMapper.map(book, BookDto.class);

        return ResponseEntity.ok().body(bookResponse);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {

        Book bookRequest = modelMapper.map(bookDto, Book.class);        // convert DTO to entity
        Book book = bookService.createBook(bookRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();
        return ResponseEntity.created(location).build();            //Status 201 - CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long id, @RequestBody BookDto bookDto) {
        Book bookReq = modelMapper.map(bookDto, Book.class);            // convert DTO to Entity
        Book book = bookService.updateBook(id, bookReq);

        BookDto bookResponse = modelMapper.map(book, BookDto.class);        // entity to DTO

        return ResponseEntity.noContent().build();      //status 200 - Successful
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().body("Book id" + 1 + "deleted"); // status 200
    }

    @PutMapping("lend/{id}")
    public ResponseEntity<LoanDto> lendBook(@PathVariable long id, @RequestBody LoanDto loanDto) {
        Book bookReq = bookService.getBookById(id);
        Person personReq = personService.getPersonById(loanDto.getPersonId());

        bookService.LendBook(id, loanDto.getPersonId());
        Book book = bookService.updateBook(id, bookReq);

        return ResponseEntity.noContent().build();      //status 200 - Successful
    }

    @PutMapping("return/{id}")
    public ResponseEntity<LoanDto> returnBook(@PathVariable long id, @RequestBody LoanDto loanDto) {
        Book bookReq = bookService.getBookById(id);
        Person personReq = personService.getPersonById(loanDto.getPersonId());

        bookReq.returnBook(personReq);

        Book book = bookService.updateBook(id, bookReq);
        personService.updatePerson(loanDto.getPersonId(), personReq);

        return ResponseEntity.noContent().build();      //status 200 - Successful
    }

}
