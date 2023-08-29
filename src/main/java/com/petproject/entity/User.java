package com.petproject.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please fill the name")
    private String username;
    @NotBlank(message = "Please fill the password")
    private String password;
    @Email(message = "Email is not correct")
    @NotBlank(message = "Please fill the email")
    private String email;
    int booksReserved;
    int booksDesired;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> purchases;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Wishlist wishlist;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer")
    private List<Rating> ratings = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    /**
     * Returns the number of books in the cart at the moment.
     */
    public String reservedBooks() {
        if (booksReserved <= 0) {
            return "";
        }
        return String.valueOf(booksReserved);
    }

    /**
     * Returns the number of books in wishlist at the moment.
     */
    public String desiredBooks() {
        if (booksDesired <= 0) {
            return "";
        }
        return String.valueOf(booksDesired);
    }

    /**
     * Used to check if a user is an admin.
     */
    public boolean isAdmin() {
        if (this.roles.contains(Role.ADMIN)) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
