package se.yrgo.service;

import se.yrgo.domain.Book;

import java.util.List;

public interface BookService {
    public void newBook(Book book);
    public void deleteBook(Book book);
    public void updateBook(Book book);
    public Book getBookByAuthor(String author);
    public Book getBookByTitle(String title);
    public Book getBookByIsbn(String isbn);
    public List<Book> getBooksByAuthor(String author);
    public List<Book> getBooks();
}
