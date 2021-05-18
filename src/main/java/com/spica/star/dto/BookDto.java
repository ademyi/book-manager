package com.spica.star.dto;

import com.spica.star.model.BookLoaned;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private List<BookLoaned> loanedList = new ArrayList<>();
}
