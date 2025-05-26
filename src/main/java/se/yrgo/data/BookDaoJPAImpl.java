package se.yrgo.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Book;
import se.yrgo.exceptions.NonExistantBookException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDaoJPAImpl implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Book book) {
        em.persist(book);
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        try {
            return em.createQuery("SELECT book FROM Book book WHERE book.isbn = :isbn", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NonExistantBookException();
        }
    }

    @Override
    public List<Book> findAllBooks() {
        try {
            return em.createQuery("SELECT book FROM Book book", Book.class)
                .getResultList();
        } catch (NoResultException e) {
            throw new NonExistantBookException();
        }
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        try {
            return em.createQuery("SELECT book FROM Book book WHERE book.author = :author", Book.class)
                    .setParameter("author", author)
                    .getResultList();
        } catch (NoResultException e) {
            throw new NonExistantBookException();
        }
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        try {
            return em.createQuery("SELECT book FROM Book book WHERE book.title = :title", Book.class)
                    .setParameter("title", title)
                    .getResultList();
        } catch (EmptyResultDataAccessException e) {
            throw new NonExistantBookException();
        }
    }
}
