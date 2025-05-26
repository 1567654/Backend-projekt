package se.yrgo.service;

import se.yrgo.domain.Customer;
import se.yrgo.exceptions.NonExistantCustomerException;

import java.util.List;

public interface CustomerService {
    public void newCustomer(Customer customer);
    public void deleteCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public Customer findCustomerById(int id);
    public Customer findCustomerByEmail(String email);
    public List<Customer> getCustomers();
}
