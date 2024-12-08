package ru.chassovnikov.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chassovnikov.com.dao.BookDAO;
import ru.chassovnikov.com.dao.PersonDAO;
import ru.chassovnikov.com.models.Book;
import ru.chassovnikov.com.models.Person;
import ru.chassovnikov.com.services.BooksService;
import ru.chassovnikov.com.services.PeopleService;
import ru.chassovnikov.com.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    private final BookDAO bookDAO;

    private final PeopleService peopleService;
    private final BooksService booksService;
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO, PeopleService peopleService, BooksService booksService) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", /*personDAO.index()*/ peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", /*personDAO.show(id)*/ peopleService.findOne(id));

        List<Book> book = booksService.showAll(id);

        if(!book.isEmpty()){
            model.addAttribute("books", booksService.showAll(id));
            for (Book k : book) {
                System.out.println(k.toString());
            }
        } else {
            model.addAttribute("absent", id);
        }

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){

//        personValidator.validate(person, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            return "people/new";
//        }

        //personDAO.save(person);
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", /*personDAO.show(id)*/ peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person")  @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){

        //personValidator.validate(person, bindingResult);

//        if(bindingResult.hasErrors()){
//            return "people/edit";
//        }

        //personDAO.update(id, person);
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        //personDAO.delete(id);
        peopleService.delete(id);
        return "redirect:/people";
    }
}
