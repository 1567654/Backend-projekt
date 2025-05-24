package se.yrgo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    @ManyToMany(mappedBy = "customers")
    private List<BorrowedBook> borrowedBooks = new ArrayList<>();

    public Customer() {}
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", books=" + borrowedBooks +
                '}';
    }
}
