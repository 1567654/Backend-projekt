package se.yrgo.data;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.exceptions.NonExistantLoanException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LoanDaoImpl implements LoanDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void loan(Loan loan) {
        em.persist(loan);
    }

    @Override
    @Transactional
    public void zeturn(Loan loan) {
        Loan managedLoan = em.merge(loan);
        em.remove(managedLoan);
    }

    @Override
    public List<Loan> findLoansByCustomer(Customer customer) {
        try {
        return em.createQuery("SELECT loan FROM Loan loan WHERE loan.customer = :customer", Loan.class)
                .setParameter("customer", customer)
                .getResultList();
        }catch (NoResultException | IllegalStateException e) {
            throw new NonExistantLoanException();
        }
    }

    @Override
    public List<Loan> getAllLoans() {
        try {
            return em.createQuery("SELECT loan from Loan loan").getResultList();
        }catch (NoResultException | IllegalStateException e) {
            throw new NonExistantLoanException();
        }
    }

    @Override
    public void extendLoan(Loan loan) {
        loan.extendLoan();
        em.merge(loan);
    }

    @Override
    public Loan getById(int id) {
        try {
            return em.createQuery("SELECT loan FROM Loan loan WHERE loan.id = :id", Loan.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (NoResultException | IllegalStateException e) {
            throw new NonExistantLoanException();
        }
    }

    @Override
    public Loan findByBook(Book book) {
        try {
            return em.createQuery("SELECT loan FROM Loan loan WHERE loan.book = :book", Loan.class)
                    .setParameter("book", book)
                    .getSingleResult();
        }catch (NoResultException | IllegalStateException e) {
            throw new NonExistantLoanException();
        }
    }
}