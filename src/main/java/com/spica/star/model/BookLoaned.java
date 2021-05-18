package com.spica.star.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_loaned")
public class BookLoaned {
    @EmbeddedId
    private BookLoanedId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id")
    private Person person;

    private String note;

    public BookLoaned(Book book, Person person) {
        this.person = person;
        this.book = book;
    }

    public BookLoaned(Book book, Person person, String note) {
        this.person = person;
        this.book = book;
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoaned that = (BookLoaned) o;
        return book.equals(that.book) && person.equals(that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, person);
    }
}
