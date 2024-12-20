package ru.chassovnikov.com.SpringBootApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chassovnikov.com.SpringBootApp.models.Book;
import ru.chassovnikov.com.SpringBootApp.models.Person;
import ru.chassovnikov.com.SpringBootApp.repositories.BookRepository;
import ru.chassovnikov.com.SpringBootApp.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class BooksService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAllByOrderByYearAsc();
    }

    public Book findOne(int id){
        Optional<Book> found = bookRepository.findById(id);
        return found.orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book book, int id){
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Optional<Book> show(String name){
        return bookRepository.findByName(name);
    }

    @Transactional
    public void assignBook(int ownerId, int bookId) {

        Person person = peopleRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Person not found"));


        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));


        person.getBooks().add(book);
        book.setOwner(person);

        bookRepository.save(book);
        peopleRepository.save(person);
    }

    public Optional<Person> getBookOwner(int bookId) {
        return bookRepository.findOwnerByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public List<Book> showAll(int owner_id) {
        return bookRepository.findByOwner_id(owner_id);
    }

    @Transactional
    public void release(int id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Person owner = book.getOwner();

        if (owner != null) {
            owner.getBooks().remove(book);
            peopleRepository.save(owner);
        }

        book.setOwner(null);
        bookRepository.save(book);
    }

}
