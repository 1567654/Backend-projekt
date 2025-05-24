package se.yrgo.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date returnDate;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Customer> customers;
    @OneToOne
    private Book book;

    public BorrowedBook() {}

    public BorrowedBook(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }
}
