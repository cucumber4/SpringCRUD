package ru.chassovnikov.com.models;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class Book {
    private int id;
    private int owner_id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String author;
    private int year;

    public Book(){}

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book(int id, int owner_id ,String name, String author, int year) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int book_id) {
        this.id = book_id;
    }

    public int getOwner_id(){
        return owner_id;
    }

    public void setOwner_id(int owner_id){
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString(){

        return  "id : " + getId() + " name " + getName() + " author : " + getAuthor();
    }
}
