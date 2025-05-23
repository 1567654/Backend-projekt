package se.yrgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.data.CustomerDao;
import se.yrgo.domain.Customer;

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
        dao.delete(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        dao.update(customer);
    }

    @Override
    public Customer findCustomerById(int id) {
        return dao.findCustomerById(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return dao.findCustomerByEmail(email);
    }

    @Override
    public List<Customer> getCustomers() {
        return dao.findAllCustomers();
    }
}
