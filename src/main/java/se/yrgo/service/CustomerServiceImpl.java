package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.CustomerDao;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Component("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao dao;
    @Override
    public void newCustomer(Customer customer) {
        dao.create(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public Customer getCustomerBy(int id) {
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        return List.of();
    }
}
