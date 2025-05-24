package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;

import java.util.List;

@Service("loanService")
public class LoanServiceImpl implements LoanService {
    private BookService bookService;
    private CustomerService customerService;

    @Autowired
    public LoanServiceImpl(BookService bookService, CustomerService customerService) {
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @Override
    public void borrowBook(Customer customer, Book book) {
        // borrow book and remove book from stock
        customerService.borrowBook(customer, book);
        bookService.deleteBook(book);

    }

    @Override
    public void returnBooks(Customer customer, List<Book> books) {
        // remove book from customer and return it to stock
        for (Book book : books) {
            customerService.returnBook(customer, book);
            bookService.newBook(book);
        }
    }
}
