package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.BookDao;
import se.yrgo.domain.Book;
import se.yrgo.domain.BookEdition;

import java.util.List;

@Transactional
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
    public Book getById(int id) {
        return dao.findById(id);
    }

    @Override
    public BookEdition getByIsbn(String isbn) {
        return dao.findByIsbn(isbn);
    }

    @Override
    public BookEdition getByAuthor(String author) {
        return dao.findByAuthor(author);
    }

    @Override
    public List<Book> getBooks() {
        return dao.findAllBooks();
    }
}
