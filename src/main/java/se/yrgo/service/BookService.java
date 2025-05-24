package se.yrgo.service;

import se.yrgo.domain.Book;
import se.yrgo.domain.BookEdition;

import java.util.List;

public interface BookService {
    public void newBook(Book book);
    public void deleteBook(Book book);
    public Book getById(int id);
    public BookEdition getByIsbn(String isbn);
    public BookEdition getByAuthor(String author);
    public List<Book> getBooks();
}
