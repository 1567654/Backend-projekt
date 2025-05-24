package se.yrgo.service;

import org.springframework.stereotype.Service;
import se.yrgo.data.CustomerDao;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;

import java.util.List;


@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    CustomerDao dao;

    public CustomerServiceImpl(CustomerDao dao) {
        this.dao = dao;
    }

    @Override
    public void newCustomer(Customer customer) {
        dao.newCustomer(customer);
    }

    @Override
    public void addBook(Customer customer, Book book) {

    }

    @Override
    public void removeBook(Customer customer, Book book) {

    }

    @Override
    public Customer findCustomerById(int id) {
        return dao.findCustomerById(id);
    }

    @Override
    public List<Book> getAllBorrowedBooks(Customer customer) {
        return dao.getAllBorrowedBooks(customer);
    }
}
