package com.spica.star.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @OneToMany( mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookLoaned> loanedList = new ArrayList<>();

    public void loanBook(Person person){
        BookLoaned loaned = new BookLoaned(this, person);
        person.getLoanedList().add(loaned);
        loanedList.add(loaned);
    }

    public void returnBook(Person person){

        for (Iterator<BookLoaned> it = loanedList.iterator(); it.hasNext();) {
            BookLoaned bookLoaned =it.next();

            if ( bookLoaned.getBook().equals(this) && bookLoaned.getPerson().equals(person)){
                it.remove();
                bookLoaned.setPerson(null);
                bookLoaned.setBook(null);
                bookLoaned.getPerson().getLoanedList().remove(bookLoaned);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }
}
