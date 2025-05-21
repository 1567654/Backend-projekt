package se.yrgo.service;

import se.yrgo.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceMockImpl implements CustomerService {

    List<Book> books = new ArrayList<Book>();

    @Override
    public void borrowBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> getAllBorrowedBooks() {
        return books;
    }

    @Override
    public void returnBook(Book book) {
        books.remove(book);
    }
}
