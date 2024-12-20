package ru.chassovnikov.com.SpringBootApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chassovnikov.com.SpringBootApp.models.Person;
import ru.chassovnikov.com.SpringBootApp.repositories.PeopleRepository;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAllByOrderByYearAsc();
    }

    public Person findOne(int id){
         Optional<Person> found = peopleRepository.findById(id);
         return  found.orElse(null) ;
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Optional<Person> show(String name){
        return peopleRepository.findByName(name);
    }
}
