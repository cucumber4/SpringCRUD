package ru.chassovnikov.com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chassovnikov.com.dao.BookDAO;
import ru.chassovnikov.com.dao.PersonDAO;
import ru.chassovnikov.com.models.Book;
import ru.chassovnikov.com.models.Person;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        System.out.println("Validating book: " + book.getName());

        if (bookDAO.show(book.getName()).isPresent()) {
            System.out.println("Book with name already exists: " + book.getName());
            errors.rejectValue("name", "", "Name should be unique :-: change name");
        }
    }
}
