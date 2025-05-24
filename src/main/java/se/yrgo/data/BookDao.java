package se.yrgo.data;

import se.yrgo.domain.Book;
import se.yrgo.domain.BookEdition;

import java.util.List;

public interface BookDao {
    public void create(Book book);
    public void delete(Book book);
    public Book findById(int id);
    public BookEdition findByIsbn(String isbn);
    public List<Book> findAllBooks();
    public BookEdition findByAuthor(String author);
}
