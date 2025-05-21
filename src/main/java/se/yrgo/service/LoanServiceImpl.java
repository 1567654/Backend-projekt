package se.yrgo.service;

import se.yrgo.domain.Book;

import java.util.List;

public class LoanServiceImpl implements LoanService {
    private BookService bookService;
    private CustomerService customerService;

    public LoanServiceImpl(BookService bookService, CustomerService customerService) {
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @Override
    public void borrowBooks(List<Book> books) {
        // borrow books and remove books from stock
        for (Book book : books) {
            customerService.borrowBook(book);
            bookService.deleteBook(book);
        }
    }

    @Override
    public void returnBooks(List<Book> books) {
        // remove book from customer and return it to stock
        for (Book book : books) {
            customerService.returnBook(book);
            bookService.newBook(book);
        }
    }
}
