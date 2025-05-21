package se.yrgo.data;

import se.yrgo.domain.Book;

import java.util.List;

public interface BookDao {
    public void create(Book book);
    public void delete(Book book);
    public void update(Book book);
    public Book findByIsbn(String isbn);
    public List<Book> findAllBooks();
    public List<Book> findBooksByAuthor(String author);
    public List<Book> findBooksByTitle(String title);
}
