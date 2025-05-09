package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.data.BookDao;
import se.yrgo.domain.Book;

import java.util.List;

@Service("bookService")
public class BookServiceProdImpl implements BookService {
    private BookDao dao;

    @Autowired
    public BookServiceProdImpl(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public void newBook(Book book) {
        dao.create(book);
    }

    @Override
    public void deleteBook(Book book) {
        dao.delete(book);
    }

    @Override
    public void updateBook(Book book) {
        dao.update(book);
    }

    @Override
    public Book getBookByAuthor(String author) {
        return dao.findByAuthor(author);
    }

    @Override
    public Book getBookByTitle(String title) {
        return dao.findByTitle(title);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return dao.findByIsbn(isbn);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return dao.findBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooks() {
        return dao.findAllBooks();
    }
}
