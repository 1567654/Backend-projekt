package se.yrgo.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    private LocalDate borrowDate;
    private int lendingDays = 30;

    public Loan() {
    }

    public Loan(Customer customer, Book book, LocalDate borrowDate) {
        this.customer = customer;
        this.book = book;
        this.borrowDate = borrowDate;
    }

    public Loan(Customer customer, Book book, LocalDate borrowDate, int lendingDays) {
        this.customer = customer;
        this.book = book;
        this.borrowDate = borrowDate;
        this.lendingDays = lendingDays;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public int getLendingDays() {
        return lendingDays;
    }
}
