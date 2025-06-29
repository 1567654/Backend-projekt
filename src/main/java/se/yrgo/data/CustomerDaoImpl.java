package se.yrgo.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Customer;
import se.yrgo.exceptions.NonExistantCustomerException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void delete(Customer customer) {
        Customer managedCustomer = em.find(Customer.class, customer.getId());
        if (managedCustomer != null) {
            em.remove(managedCustomer);
        }
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
            throw new NonExistantCustomerException();
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        try {
            return em.createQuery("SELECT customer FROM Customer customer WHERE customer.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NonExistantCustomerException();
        }
    }


    @Override
    public List<Customer> findAllCustomers() {
        try {
            return em.createQuery("SELECT customer FROM Customer customer", Customer.class)
                    .getResultList();
        } catch (NoResultException e) {
            throw new NonExistantCustomerException();
        }
    }
}
