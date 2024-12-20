package ru.chassovnikov.com.SpringBootApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chassovnikov.com.SpringBootApp.models.Book;
import ru.chassovnikov.com.SpringBootApp.services.BooksService;


@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        System.out.println("Validating book: " + book.getName());

        if (booksService.show(book.getName()).isPresent()) {
            System.out.println("Book with name already exists: " + book.getName());
            errors.rejectValue("name", "", "Name should be unique :-: change name");
        }
    }
}
