package se.yrgo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.exceptions.NonExistantLoanException;
import se.yrgo.service.BookService;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@ContextConfiguration( {"/applicationTest.xml", "/datasource-test.xml" })
public class LoanTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LoanService loanService;

    @Test
    public void testCreateLoan() {
        Book book = new Book("Java", "Jack", "123456789");
        Customer customer1 = new Customer("John", "John123@mail.com");
        Customer customer2 = new Customer("Karl", "karl123@mail.com");
        bookService.newBook(book);
        customerService.newCustomer(customer1);
        customerService.newCustomer(customer2);
        Loan loan1 = new Loan(customer1, book, LocalDate.now());
        Loan loan2 = new Loan(customer2, book, LocalDate.now());
        loanService.loan(loan1);
        loanService.loan(loan2);

        List<Loan> loans = loanService.getAllLoans();
        assertEquals(2, loans.size());
    }

    @Test
    public void testFindNonExistingLoan() {
        Book book = new Book("Java", "Jack", "123456789");

        assertThrows(NonExistantLoanException.class, () -> loanService.findByBook(book));
    }

    @Test
    public void testReturnLoan() {
        Book book = new Book("Java", "Jack", "123456789");
        Customer customer1 = new Customer("John", "John123@mail.com");
        Customer customer2 = new Customer("Karl", "karl123@mail.com");
        bookService.newBook(book);
        customerService.newCustomer(customer1);
        customerService.newCustomer(customer2);
        Loan loan1 = new Loan(customer1, book, LocalDate.now());
        Loan loan2 = new Loan(customer2, book, LocalDate.now());
        loanService.loan(loan1);
        loanService.loan(loan2);

        loanService.zeturn(loan1);
        assertEquals(1, loanService.getAllLoans().size());
    }

    @Test
    public void testExtendLoan() {
        Book book = new Book("Java", "Jack", "123456789");
        Customer customer1 = new Customer("John", "John123@mail.com");
        bookService.newBook(book);
        customerService.newCustomer(customer1);
        Loan loan = new Loan(customer1, book, LocalDate.now());
        loanService.loan(loan);

        loanService.extendLoan(loan);
        assertEquals(60, loanService.getAllLoans().getFirst().getLendingDays());
    }

    @Test
    public void testFindLoansByCustomer() {
        Book book = new Book("Java", "Jack", "123456789");
        Customer customer = new Customer("John", "John123@mail.com");
        bookService.newBook(book);
        customerService.newCustomer(customer);
        Loan loan = new Loan(customer, book, LocalDate.now());
        loanService.loan(loan);

        assertEquals(loan, loanService.findLoansByCustomer(customer).getFirst());
    }

    @Test
    public void testFindByBook() {
        Book book = new Book("Java", "Jack", "123456789");
        Customer customer = new Customer("John", "John123@mail.com");
        customerService.newCustomer(customer);
        bookService.newBook(book);
        Loan loan = new Loan(customer, book, LocalDate.now());
        loanService.loan(loan);

        assertEquals(loan, loanService.findByBook(book));
    }
}
