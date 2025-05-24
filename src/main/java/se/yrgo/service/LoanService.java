package se.yrgo.service;

import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;

import java.util.List;

public interface LoanService {
    public void loan(Loan loan);
    public void zeturn(Loan loan);
    public void returnAllLoansForCustomer(Customer customer);
    public void extendLoan(Loan loan);
    public List<Loan> findLoansByCustomer(Customer customer);
    public Loan getById(int id);
    public List<Loan> getAllLoans();
    public Loan findByBook(Book book);
}
