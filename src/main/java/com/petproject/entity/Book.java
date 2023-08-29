package com.petproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private double rating;
    private String coverImage;

    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Author> authors;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Order> purchases = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<>();

    public Book() {
    }

    public Book(String name, double price, int quantity, String coverImage, BookGenre bookGenre, List<Author> authors) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.coverImage = coverImage;
        this.bookGenre = bookGenre;
        this.authors = authors;
    }

    public double averageRating() {
        double avgRating = ratings.stream()
                .mapToDouble(Rating::getMark)
                .average()
                .orElse(0.0);

        BigDecimal bd = new BigDecimal(avgRating);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        avgRating = bd.doubleValue();

        return avgRating;
    }
}
