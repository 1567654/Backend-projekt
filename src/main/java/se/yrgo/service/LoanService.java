package se.yrgo.service;

import se.yrgo.domain.Book;

import java.util.List;

public interface LoanService {
    public void borrowBooks(List<Book> books);
    public void returnBooks(List<Book> books);
}
