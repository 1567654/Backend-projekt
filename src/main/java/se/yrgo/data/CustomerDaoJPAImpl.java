package se.yrgo.data;

import org.springframework.stereotype.Repository;
import se.yrgo.domain.Book;
import se.yrgo.domain.BorrowedBook;
import se.yrgo.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDaoJPAImpl implements CustomerDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void newCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void addBook(Customer customer, Book book) {
        // update customer
        
    }

    @Override
    public void removeBook(Customer customer, Book book) {

    }

    @Override
    public Customer findCustomerById(int id) {
        return em.createQuery("SELECT customer FROM Customer customer WHERE customer.id= :id", Customer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Book> getAllBorrowedBooks(Customer customer) {
        List<Book> books = new ArrayList<>();
        for (BorrowedBook book : customer.getBorrowedBooks()) {
            books.add(book.getBook());
        }
        return books;
    }

}
