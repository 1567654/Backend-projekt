package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.LoanDao;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;

import java.util.List;

@Transactional
@Component("loanService")
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanDao dao;

    @Override
    public void loan(Loan loan) {
        dao.loan(loan);
    }

    @Override
    public void zeturn(Loan loan) {
        dao.zeturn(loan);
    }

    @Override
    public List<Loan> findLoansByCustomer(Customer customer) {
        return dao.findLoansByCustomer(customer);
    }
}
