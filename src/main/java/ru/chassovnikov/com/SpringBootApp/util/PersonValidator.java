package ru.chassovnikov.com.SpringBootApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chassovnikov.com.SpringBootApp.models.Person;
import ru.chassovnikov.com.SpringBootApp.services.PeopleService;


@Component
public class PersonValidator implements Validator {


    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {

        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleService.show(person.getName()).isPresent()){
            errors.rejectValue("name", "", "Name should be unique :-: change name");
        }

    }
}
