package se.yrgo.data;

import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Book;

import java.util.List;

@Repository
public class BookDaoHibernate implements BookDao {
    private HibernateTemplate template;

    @Autowired
    public BookDaoHibernate(HibernateTemplate template) {
        this.template = template;
    }

    @Override
    public void create(Book book) {
        template.save(book);
    }

    @Override
    public void delete(Book book) {
        template.delete(book);
    }

    @Override
    public void update(Book book) {
        template.update(book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return ;
    }

    @Override
    public Book findByTitle(String title) {
        return null;
    }

    @Override
    public Book findByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> findAllBooks() {
        return List.of();
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return List.of(template.execute(session -> session
                .createQuery("from Book where author = :author")
                .setParameter("author", author).list()));
    }
}
