package ru.chassovnikov.com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.chassovnikov.com.models.Book;
import ru.chassovnikov.com.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index(){
        return jdbcTemplate.query("SELECT id, name, year, author FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    //для проверки есть ли уже таое имя или нет
    public Optional<Book> show(String name){
        return jdbcTemplate.query("SELECT name FROM Book WHERE name = ?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Book show(int book_id){
        return jdbcTemplate.query("SELECT id, owner_id, name, year, author FROM Book WHERE id = ?", new Object[]{book_id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }


    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book (name , author , year) VALUES (?,?,?) ", book.getName(), book.getAuthor(), book.getYear());
    }
    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE Book SET name=?, year=? WHERE id=?", book.getName(), book.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public void assignBook(int owner_id, int book_id){
        jdbcTemplate.update("UPDATE Book SET owner_id = ? WHERE id = ?", owner_id , book_id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT person.* FROM person JOIN book ON person.id = book.owner_id WHERE book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
