package se.yrgo.data;

import org.springframework.stereotype.Repository;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LoanDaoJPAImpl implements LoanDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void loan(Loan loan) {
        em.persist(loan);
    }

    @Override
    public void zeturn(Loan loan) {
        em.remove(loan);
    }

    @Override
    public void extend(Loan loan) {
        em.merge(loan);
    }

    @Override
    public List<Loan> findLoansByCustomer(Customer customer) {
        return em.createQuery("SELECT loan FROM Loan loan WHERE loan.customer = :customer", Loan.class)
                .setParameter("customer", customer)
                .getResultList();
    }
}