package se.yrgo.data;

import org.springframework.stereotype.Repository;
import se.yrgo.domain.Book;
import se.yrgo.domain.BookEdition;

import javax.persistence.EntityManager;
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
    public Book findById(int id) {
        return em.createQuery("select b from Book b where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public BookEdition findByIsbn(String isbn) {
        return em.createQuery("SELECT book FROM Book book WHERE book.isbn = :isbn", BookEdition.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

    @Override
    public List<Book> findAllBooks() {
        return em.createQuery("SELECT book FROM Book book", Book.class)
                .getResultList();
    }

    @Override
    public BookEdition findByAuthor(String author) {
        return em.createQuery("SELECT book FROM Book book WHERE book.author = :author", BookEdition.class)
                .setParameter("author", author)
                .getSingleResult();
    }

}
