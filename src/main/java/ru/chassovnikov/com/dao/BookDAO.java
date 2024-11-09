package ru.chassovnikov.com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
        return jdbcTemplate.query("SELECT book_id, name, year, author FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    //для проверки есть ли уже такая книга или нет
    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE name = ?", new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public Book show(int book_id){
        return jdbcTemplate.query("SELECT book_id, name, year, author FROM Book WHERE book_id = ?", new Object[]{book_id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book (name , author , year) VALUES (?,?,?) ", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int book_id, Book book){
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ? WHERE book_id = ?", book.getName(), book.getAuthor(), book.getYear(), book_id);
    }

    public void delete(int book_id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", book_id);
    }

}
