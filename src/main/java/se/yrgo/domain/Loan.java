package se.yrgo.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    public void extendLoan() {
        this.lendingDays += 30;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getId() {
        return id;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public int getLendingDays() {
        return lendingDays;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book +
                ", customer=" + customer +
                ", borrowDate=" + borrowDate +
                ", lendingDays=" + lendingDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id && lendingDays == loan.lendingDays && Objects.equals(book, loan.book) && Objects.equals(customer, loan.customer) && Objects.equals(borrowDate, loan.borrowDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, customer, borrowDate, lendingDays);
    }
}
