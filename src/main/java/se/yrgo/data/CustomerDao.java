package se.yrgo.data;

import se.yrgo.domain.Book;
import se.yrgo.domain.BorrowedBook;
import se.yrgo.domain.Customer;

import java.util.List;

public interface CustomerDao {
    public void newCustomer(Customer customer);
    public void addBook(Customer customer, Book book);
    public void removeBook(Customer customer, Book book);
    public Customer findCustomerById(int id);
    public List<Book> getAllBorrowedBooks(Customer customer);
}
