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
import ru.chassovnikov.com.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;
    private final BooksService booksService;
    private final PeopleService peopleService;


    public BookController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO, BooksService booksService, PeopleService peopleService, PeopleService peopleService1) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
        this.booksService = booksService;
        this.peopleService = peopleService1;
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booksService.findAll());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {

        Book book = booksService.findOne(id);
        Optional<Person> owner = booksService.getBookOwner(id);

        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get().getName());
        } else {
            model.addAttribute("owner", "Нет владельца");
        }

        if (book.getOwner() == null) {
            model.addAttribute("list", true);
            model.addAttribute("people", peopleService.findAll());
        } else {
            model.addAttribute("list", false);
        }

        model.addAttribute("book", book);

        return "book/show";
    }



    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book){
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){

//        bookValidator.validate(book, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            return "book/new";
//        }

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")  @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){

//        bookValidator.validate(book, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            return "book/edit";
//        }

        booksService.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/add/{id}")
    public String makeAdmin(@RequestParam("ownerId") int ownerId, @PathVariable("id") int bookId) {
        System.out.println("Назначено пернсон айди: " + ownerId + " Бук айди: " + bookId);

        booksService.assignBook(ownerId, bookId);

        return "redirect:/books";
    }

    @PatchMapping("/release/{id}")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books";
    }

}
