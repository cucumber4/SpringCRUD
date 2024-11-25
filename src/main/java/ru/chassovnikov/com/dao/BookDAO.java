package ru.chassovnikov.com.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.chassovnikov.com.models.Book;
import ru.chassovnikov.com.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index(){
        //return jdbcTemplate.query("SELECT id, name, year, author FROM Book", new BeanPropertyRowMapper<>(Book.class));
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("Select b FROM Book b", Book.class).getResultList();
        return books;
    }

    //для проверки есть ли уже таое имя или нет
    public Optional<Book> show(String name){
        return jdbcTemplate.query("SELECT name FROM Book WHERE name = ?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }
    @Transactional
    public Book show(int book_id){
        //return jdbcTemplate.query("SELECT id, owner_id, name, year, author FROM Book WHERE id = ?", new Object[]{book_id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        return book;
    }

    @Transactional
    public void save(Book book){
        //jdbcTemplate.update("INSERT INTO Book (name , author , year) VALUES (?,?,?) ", book.getName(), book.getAuthor(), book.getYear());
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }
    @Transactional
    public void update(int id, Book updatedbook){
        //jdbcTemplate.update("UPDATE Book SET name=?, year=? WHERE id=?", book.getName(), book.getYear(), id);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setName(updatedbook.getName());
        book.setAuthor(updatedbook.getAuthor());
        book.setYear(updatedbook.getYear());
    }

    @Transactional
    public void delete(int id){
        //jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.remove(book);
    }

    @Transactional
    public void assignBook(int owner_id, int book_id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, owner_id);
        Book book = session.get(Book.class, book_id);

        person.getBooks().add(book);
        book.setOwner(person);

        session.update(book);
        session.update(person);
    }

    @Transactional
    public Optional<Person> getBookOwner(int bookId){
        //return jdbcTemplate.query("SELECT person.* FROM person JOIN book ON person.id = book.owner_id WHERE book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "SELECT b.owner FROM Book b WHERE b.id = :bookId", Person.class)
                .setParameter("bookId", bookId)
                .getResultStream()
                .findFirst();
    }

    @Transactional
    public List<Book> showAll(int id){
        //return jdbcTemplate.query("Select * FROM book WHERE owner_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT b FROM Book b").getResultList();
    }

    @Transactional
    public void release(int id) {
        //jdbcTemplate.update("UPDATE Book SET owner_id = NULL WHERE id = ?", id);
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, id);
        Person person = session.get(Person.class, book.getOwnerId());

        person.getBooks().remove(book);

        book.setOwner(null);
        session.update(book);
    }

}
