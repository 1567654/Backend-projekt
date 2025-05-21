package se.yrgo.service;

import se.yrgo.domain.Book;

import java.util.List;

public interface CustomerService {
    public void borrowBook(Book book);
    public List<Book> getAllBorrowedBooks();
    public void returnBook(Book book);
}
