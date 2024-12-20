package ru.chassovnikov.com.SpringBootApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chassovnikov.com.SpringBootApp.models.Person;


import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    List<Person> findAllByOrderByYearAsc();
    Optional<Person> findByName(String name);
}
