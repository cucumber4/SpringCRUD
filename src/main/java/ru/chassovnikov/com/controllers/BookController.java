package ru.chassovnikov.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chassovnikov.com.dao.BookDAO;
import ru.chassovnikov.com.models.Book;
import ru.chassovnikov.com.models.Person;
import ru.chassovnikov.com.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    public BookController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model){

        model.addAttribute("books", bookDAO.index());

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){

        model.addAttribute("book", bookDAO.show(id));

        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "book/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){

        model.addAttribute("book", bookDAO.show(id));

        return "book/edit";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book){

        bookDAO.save(book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book")  @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("Есть ошибка в BindingResult");
            bindingResult.getAllErrors().forEach(error -> System.out.println(error));
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
}
