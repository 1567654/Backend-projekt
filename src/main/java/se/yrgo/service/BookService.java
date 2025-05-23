package se.yrgo.service;

import se.yrgo.domain.Book;

import java.util.List;

public interface BookService {
    public void newBook(Book book);
    public void deleteBook(Book book);
    public void updateBook(Book book);
    public Book findBookByIsbn(String isbn);
    public List<Book> getBooksByAuthor(String author);
    public List<Book> getBooksByTitle(String title);
    public List<Book> getBooks();
}
