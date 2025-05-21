package se.yrgo.data;

import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;

import java.util.List;

public interface LoanDao {
    public void loan(Loan loan);

    public void zeturn(Loan loan);

    public void extend(Loan loan);

    public List<Loan> findLoansByCustomer(Customer customer);
}
