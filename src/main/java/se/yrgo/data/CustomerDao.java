package se.yrgo.data;

import se.yrgo.domain.Customer;
import se.yrgo.exceptions.NonExistantCustomerException;

import java.util.List;

public interface CustomerDao {
    public void create(Customer customer);
    public void delete(Customer customer);
    public void update(Customer customer);
    public Customer findCustomerById(int id);
    public Customer findCustomerByEmail(String email);
    public List<Customer> findAllCustomers();
}
