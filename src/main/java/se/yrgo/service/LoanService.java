package se.yrgo.service;

import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;

import java.util.List;

public interface LoanService {
    public void borrowBook(Customer customer, Book book);
    public void returnBook(Customer customer, Book book);
}
