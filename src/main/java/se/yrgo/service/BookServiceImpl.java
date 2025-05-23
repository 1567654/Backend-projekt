package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BookDao;
import se.yrgo.domain.Book;

import java.util.List;

@Transactional
@Component("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao dao;

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
    public Book findBookByIsbn(String isbn) {
        return dao.findByIsbn(isbn);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return dao.findBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return dao.findBooksByTitle(title);
    }

    @Override
    public List<Book> getBooks() {
        return dao.findAllBooks();
    }
}
