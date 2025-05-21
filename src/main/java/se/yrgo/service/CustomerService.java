package se.yrgo.service;

import se.yrgo.domain.Customer;

import java.util.List;

public interface CustomerService {
    public void newCustomer(Customer customer);
    public void deleteCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public Customer getCustomerBy(int id);
    public List<Customer> getCustomers();
}
