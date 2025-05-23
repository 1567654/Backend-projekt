package se.yrgo.data;

import org.springframework.stereotype.Repository;
import se.yrgo.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerDaoJPAImpl implements CustomerDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void delete(Customer customer) {
        em.remove(customer);
    }

    @Override
    public void update(Customer customer) {
        em.merge(customer);
    }

    @Override
    public Customer findCustomerById(int id) {
        try {
            return em.createQuery("SELECT customer FROM Customer customer WHERE customer.id = :id", Customer.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        try {
            return em.createQuery("SELECT customer FROM Customer customer WHERE customer.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public List<Customer> findAllCustomers() {
        return em.createQuery("SELECT customer FROM Customer customer", Customer.class)
                .getResultList();
    }
}
