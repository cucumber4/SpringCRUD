package ru.chassovnikov.com.SpringBootApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.chassovnikov.com.SpringBootApp.models.Book;
import ru.chassovnikov.com.SpringBootApp.models.Person;


import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByOrderByYearAsc();
    Optional<Book> findByName(String name);

    @Query("SELECT b.owner FROM Book b WHERE b.id = :bookId")
    Optional<Person> findOwnerByBookId(@Param("bookId") int bookId);
    List<Book> findByOwner_id(int owner_id);

    @Modifying
    @Query("UPDATE Book b SET b.owner = NULL WHERE b.id = :id")
    void releaseOwnerById(@Param("id") int id);
}
