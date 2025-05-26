package se.yrgo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.domain.Customer;
import se.yrgo.exceptions.NonExistantCustomerException;
import se.yrgo.service.CustomerService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@ContextConfiguration( {"/applicationTest.xml", "/datasource-test.xml" })
public class CustomerTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer("Hans", "hans123@mail.com");
        customerService.newCustomer(customer);

        assertEquals(1, customerService.getCustomers().size());
    }

    @Test
    public void testNonExistingCustomer() {
        assertThrows(NonExistantCustomerException.class, () -> customerService.findCustomerByEmail("hans123@mail.com"));
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer("Hans", "hans123@mail.com");
        customerService.newCustomer(customer);
        customerService.deleteCustomer(customer);

        assertEquals(0, customerService.getCustomers().size());
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer("Hans", "hans123@mail.com");
        customerService.newCustomer(customer);
        customer.setName("Rick");

        customerService.updateCustomer(customer);
        assertEquals("Rick", customerService.findCustomerByEmail("hans123@mail.com").getName());
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer("Hans", "hans123@mail.com");
        customerService.newCustomer(customer);

        assertEquals(customer, customerService.findCustomerById(customer.getId()));
    }

    @Test
    public void testFindCustomerByEmail() {
        Customer customer = new Customer("Hans", "hans123@mail.com");
        customerService.newCustomer(customer);

        assertEquals(customer.getEmail(), customerService.findCustomerByEmail("hans123@mail.com").getEmail());
    }

    @Test
    public void testFindAllCustomers() {
        Customer customer1 = new Customer("Hans", "hans123@mail.com");
        customerService.newCustomer(customer1);
        Customer customer2 = new Customer("Carl", "carl123@mail.com");
        customerService.newCustomer(customer2);

        assertEquals(2, customerService.getCustomers().size());
    }
}
