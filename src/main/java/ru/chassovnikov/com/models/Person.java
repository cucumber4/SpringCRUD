package ru.chassovnikov.com.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @Min(value = 0, message = "cannot be less tha zero")
    private int year;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 255, message = "name should be between 2 and 255")
    private String name;


    public Person(){}
    public Person(int id, String name , int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
