package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.data.CustomerDao;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.service.BookService;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Client {
    public static void main(String[] args) {
//        JFrame frame = new JFrame("Library");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setMinimumSize(new Dimension(800, 600));
//        frame.setVisible(true);


        ClassPathXmlApplicationContext container =
                new ClassPathXmlApplicationContext("application.xml");


        try {
            Book test = new Book("Bajs", "Göran", "12345");
            BookService bookService = container.getBean(BookService.class);
            bookService.newBook(test);

            Customer customer = new Customer("Göran Kungsson", "goran@kungsson.se");
            CustomerService customerService = container.getBean(CustomerService.class);
            customerService.newCustomer(customer);

            Loan loan = new Loan(customer, test, LocalDate.now());
            LoanService loanService = container.getBean(LoanService.class);
            loanService.loan(loan);

        } finally {
            container.close();
        }
    }
}
