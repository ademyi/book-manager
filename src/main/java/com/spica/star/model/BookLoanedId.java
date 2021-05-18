package com.spica.star.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookLoanedId implements Serializable {
    @Column( name = "book_id")
    private Long bookId;

    @Column( name = "person_id")
    private Long personId;

    public BookLoanedId(){}

    public BookLoanedId(Long bookId, Long personId) {
        this.bookId = bookId;
        this.personId = personId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoanedId that = (BookLoanedId) o;
        return Objects.equals(bookId, that.bookId) && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, personId);
    }
}
