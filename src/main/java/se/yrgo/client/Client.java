package se.yrgo.client;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.service.BookService;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;

import java.io.IOException;
import java.time.LocalDate;

public class Client {
    public static void main(String[] args) throws IOException {
        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            CustomerService customerService = container.getBean(CustomerService.class);
            BookService bookService = container.getBean(BookService.class);
            LoanService loanService = container.getBean(LoanService.class);

            addNewCustomer(customerService);
            addNewBook(bookService);
            addNewLoan(customerService, bookService, loanService);


            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Main Menu");

            Panel panel = getPanel(textGUI, window, container);

            window.setComponent(panel);
            textGUI.addWindowAndWait(window);
        }
    }

    private static void addNewBook(BookService bookService) {
        Book test = new Book("Middlemarch", "George Eliot", "123");
        bookService.newBook(test);
    }

    private static void addNewCustomer(CustomerService customerService) {
        Customer customer = new Customer("Hampus Ram", "hampus@ramson.ru");
        customerService.newCustomer(customer);
    }

    private static void addNewLoan(CustomerService customerService, BookService bookService, LoanService loanService) {
        Loan loan = new Loan(customerService.findCustomerByEmail("hampus@ramson.ru"), bookService.findBookByIsbn("123"), LocalDate.now());
        loanService.loan(loan);
    }

    private static Panel getPanel(WindowBasedTextGUI textGUI, BasicWindow window, ClassPathXmlApplicationContext container) {
        CustomerService customerService = container.getBean(CustomerService.class);
        BookService bookService = container.getBean(BookService.class);
        LoanService loanService = container.getBean(LoanService.class);

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button booksButton = new Button("1. Books", () -> openBooksMenu(textGUI, customerService, bookService, loanService));
        Button customersButton = new Button("2. Customers", () -> openCustomersMenu(textGUI, customerService, bookService, loanService));
        Button loansButton = new Button("3. Loans", () -> openLoansMenu(textGUI, customerService, bookService, loanService));
        Button exitButton = new Button("Exit", () -> System.exit(0));

        panel.addComponent(booksButton);
        panel.addComponent(customersButton);
        panel.addComponent(loansButton);
        panel.addComponent(exitButton);
        return panel;
    }

    private static void openBooksMenu(WindowBasedTextGUI textGUI, CustomerService customerService, BookService bookService, LoanService loanService) {
        BasicWindow booksWindow = new BasicWindow("Books");

        Panel booksPanel = new Panel(new GridLayout(1));

        Button createButton = new Button("1. Create Book", () -> BookMenu.Create(textGUI, bookService));
        Button updateButton = new Button("2. Update Book", () -> BookMenu.Update(textGUI, bookService));
        Button deleteButton = new Button("3. Delete Book", () -> BookMenu.Delete(textGUI, bookService));
        Button findBookByIdButton = new Button("4. Find Book By ISBN", () -> BookMenu.findBookByIsbn(textGUI, bookService));
        Button showAllBooksButton = new Button("5. Show All Books", () -> BookMenu.showAllBooks(textGUI, bookService));
        Button backButton = new Button("Back", booksWindow::close);

        booksPanel.addComponent(createButton);
        booksPanel.addComponent(updateButton);
        booksPanel.addComponent(deleteButton);
        booksPanel.addComponent(findBookByIdButton);
        booksPanel.addComponent(showAllBooksButton);
        booksPanel.addComponent(backButton);

        booksWindow.setComponent(booksPanel);
        textGUI.addWindowAndWait(booksWindow);
    }


    private static void openCustomersMenu(WindowBasedTextGUI textGUI, CustomerService customerService, BookService bookService, LoanService loanService) {
        BasicWindow customerWindow = new BasicWindow("Customers");

        Panel customerPanel = new Panel(new GridLayout(1));

        Button createButton = new Button("1. Create Customer", () -> CustomerMenu.Create(textGUI, customerService));
        Button updateButton = new Button("2. Update Customer", () -> CustomerMenu.Update(textGUI, customerService));
        Button deleteButton = new Button("3. Delete Customer", () -> CustomerMenu.Delete(textGUI, customerService, loanService));
        Button findCustomerByIdButton = new Button("4. Find Customer By Email", () -> CustomerMenu.FindByEmail(textGUI, customerService));
        Button showAllCustomersButton = new Button("5. Show All Customers", () -> CustomerMenu.showAllCustomers(textGUI, customerService));
        Button backButton = new Button("Back", customerWindow::close);

        customerPanel.addComponent(createButton);
        customerPanel.addComponent(updateButton);
        customerPanel.addComponent(deleteButton);
        customerPanel.addComponent(findCustomerByIdButton);
        customerPanel.addComponent(showAllCustomersButton);
        customerPanel.addComponent(backButton);

        customerWindow.setComponent(customerPanel);
        textGUI.addWindowAndWait(customerWindow);
    }

    private static void openLoansMenu(WindowBasedTextGUI textGUI, CustomerService customerService, BookService bookService, LoanService loanService) {
        BasicWindow loanWindow = new BasicWindow("Loans");

        Panel loanPanel = new Panel(new GridLayout(1));

        Button createButton = new Button("1. Create Loan", () -> LoanMenu.create(textGUI, loanService, customerService, bookService));
        Button updateButton = new Button("2. Extend Loan", () -> LoanMenu.extend(textGUI, loanService, customerService));
        Button returnButton = new Button("3. Return Loan", () -> LoanMenu.zeturn(textGUI, loanService, customerService));
        Button showAllLoansButton = new Button("4. View Customer Loans", () -> LoanMenu.viewCustomerLoans(textGUI, loanService, customerService));
        Button backButton = new Button("Back", loanWindow::close);

        loanPanel.addComponent(createButton);
        loanPanel.addComponent(updateButton);
        loanPanel.addComponent(returnButton);
        loanPanel.addComponent(showAllLoansButton);
        loanPanel.addComponent(backButton);

        loanWindow.setComponent(loanPanel);
        textGUI.addWindowAndWait(loanWindow);
    }

    private static void showInfo(WindowBasedTextGUI gui, String message) {
        MessageDialog.showMessageDialog(gui, "Info", message);
    }
}