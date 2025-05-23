package se.yrgo.service;

import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.domain.Loan;

import java.util.List;

public interface LoanService {
    public void loan(Loan loan);
    public void zeturn(Loan loan);
    public void returnAllLoansForCustomer(Customer customer);
    public List<Loan> findLoansByCustomer(Customer customer);
}
