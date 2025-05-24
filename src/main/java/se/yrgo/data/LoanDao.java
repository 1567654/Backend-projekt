package se.yrgo.data;

import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.domain.Book;

import java.util.List;

public interface LoanDao {
    public void loan(Loan loan);

    public void zeturn(Loan loan);

    public List<Loan> findLoansByCustomer(Customer customer);

    public List<Loan> getAllLoans();

    public void extendLoan(Loan loan);

    public Loan getById(int id);

    public Loan findByBook(Book book);
}
