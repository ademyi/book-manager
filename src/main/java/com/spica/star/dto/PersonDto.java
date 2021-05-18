package com.spica.star.dto;

import com.spica.star.model.BookLoaned;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonDto {
    private Long id;
    private String firstName;
    private String surname;
    private List<BookLoaned> loanedList = new ArrayList<>();

}
