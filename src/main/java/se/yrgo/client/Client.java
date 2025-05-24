package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.service.BookService;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext container =
                new ClassPathXmlApplicationContext("application.xml");

        try {
            BookService bookService = (BookService) container.getBean("bookService");
            bookService.newBook(new Book("Java", "Jane", "1233649916"));
            for (Book book : bookService.getBooks()) {
                System.out.println("should have 1 book: "+book);
            }
            bookService.deleteBook(bookService.getBookByIsbn("1233649916").getBooks().getFirst());
            System.out.println("should be empty: "+ bookService.getBooks());

            CustomerService customerService = (CustomerService) container.getBean("customerService");
            customerService.newCustomer(new Customer("John", "john@doe.com"));
            Customer customer = customerService.findCustomerById(0);
            System.out.println("should have 1 customer: "+customer);

//            LoanService loanService = (LoanService) container.getBean("loanService");
//            loanService.borrowBooks(customer, new ArrayList<>(bookService.getBooks()));
//            System.out.println(customerService.getAllBorrowedBooks(customer));
//            loanService.returnBooks(customer, new ArrayList<>(bookService.getBooks()));
//            System.out.println("Get by ISBN: " + bookService.getBookByIsbn("1233649916"));
        } finally {
            container.close();
        }
    }
}
