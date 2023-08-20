package com.petproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private double price;

    private int quantity;


    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Author> authors;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Book() {
    }

    public Book(String name, double price, int quantity, List<Author> authors) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.authors = authors;
    }
}
