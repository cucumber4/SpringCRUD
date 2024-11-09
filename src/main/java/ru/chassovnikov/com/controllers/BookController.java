package ru.chassovnikov.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chassovnikov.com.dao.BookDAO;
import ru.chassovnikov.com.dao.PersonDAO;
import ru.chassovnikov.com.models.Book;
import ru.chassovnikov.com.models.Person;
import ru.chassovnikov.com.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person ) {
        Book book = bookDAO.show(id);
        Optional<Person> sorson = bookDAO.getBookOwner(id);

        if (sorson.isPresent()) {
            model.addAttribute("owner", sorson.get().getName());
        } else {
            model.addAttribute("owner", "Нет владельца"); // или другое сообщение по умолчанию
        }

        if (book.getOwnerId() == null) {
            System.out.println("null Integer");
            model.addAttribute("list", personDAO.index());
        }

        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());

        return "book/show";
    }


    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book){
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "book/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")  @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "book/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/add/{id}")
    public String makeAdmin(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        System.out.println(person.getId() + ", " + id);

        bookDAO.assignBook(person.getId(), id);

        return "redirect:/books";
    }

}
