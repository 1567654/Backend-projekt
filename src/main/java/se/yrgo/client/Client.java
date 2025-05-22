package se.yrgo.client;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Book;
import se.yrgo.service.BookService;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;

import java.io.IOException;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {
        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {
            Screen screen = new DefaultTerminalFactory().createScreen();
            screen.startScreen();

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Main Menu");

            Panel panel = getPanel(textGUI, window, container);

            window.setComponent(panel);
            textGUI.addWindowAndWait(window);
            
//            Book test = new Book("Bajs", "Göran", "12345");
//            BookService bookService = container.getBean(BookService.class);
//            bookService.newBook(test);
//
//            Customer customer = new Customer("Göran Kungsson", "goran@kungsson.se");
//            CustomerService customerService = container.getBean(CustomerService.class);
//            customerService.newCustomer(customer);
//
//            Loan loan = new Loan(customer, test, LocalDate.now());
//            LoanService loanService = container.getBean(LoanService.class);
//            loanService.loan(loan);
        }
    }

    private static Panel getPanel(WindowBasedTextGUI textGUI, BasicWindow window, ClassPathXmlApplicationContext container) {
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button booksButton = new Button("1. Books", () -> openBooksMenu(textGUI, container.getBean(BookService.class)));
        Button customersButton = new Button("2. Customers", () -> openCustomersMenu(textGUI, container.getBean(CustomerService.class)));
        Button loansButton = new Button("3. Loans", () -> openLoansMenu(textGUI, container.getBean(LoanService.class)));
        Button exitButton = new Button("Exit", () -> System.exit(0));

        panel.addComponent(booksButton);
        panel.addComponent(customersButton);
        panel.addComponent(loansButton);
        panel.addComponent(exitButton);
        return panel;
    }

    private static void openBooksMenu(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow booksWindow = new BasicWindow("Books");

        Panel booksPanel = new Panel(new GridLayout(1));

        Button createButton = new Button("1. Create Book", () -> BookMenu.Create(textGUI, bookService));
        Button updateButton = new Button("2. Update Book", () -> BookMenu.Update(textGUI, bookService));
        Button deleteButton = new Button("3. Delete Book", () -> showInfo(textGUI, "Delete customer (not implemented)"));
        Button findBookByIdButton = new Button("4. Find Book By Id", () -> showInfo(textGUI, "Delete customer (not implemented)"));
        Button showAllBooksButton = new Button("5. Show All Books", () -> showAllBooksMenu(textGUI, bookService));
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

    private static void showAllBooksMenu(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow allBooksWindow = new BasicWindow("All Books");
        Panel booksPanel = new Panel(new GridLayout(1));

        Table<String> table = new Table<String>("Id", "Title", "Author", "ISBN");
        List<Book> allBooks = bookService.getBooks();
        for(Book book : allBooks) {
            table.getTableModel().addRow(String.valueOf(book.getId()), book.getTitle(), book.getAuthor(), book.getIsbn());
        }
        Button backButton = new Button("Back", allBooksWindow::close);

        booksPanel.addComponent(table);

        booksPanel.addComponent(backButton);

        allBooksWindow.setComponent(booksPanel);
        textGUI.addWindowAndWait(allBooksWindow);
    }

    private static void openCustomersMenu(WindowBasedTextGUI textGUI, CustomerService customerService) {
        BasicWindow customerWindow = new BasicWindow("Customers");

        Panel customerPanel = new Panel(new GridLayout(1));

        Button createButton = new Button("8. Create Customer", () -> showInfo(textGUI, "Create customer (not implemented)"));
        Button updateButton = new Button("8. Update Customer", () -> showInfo(textGUI, "Update customer (not implemented)"));
        Button deleteButton = new Button("9. Delete Customer", () -> showInfo(textGUI, "Delete customer (not implemented)"));
        Button findCustomerByIdButton = new Button("9. Find Customer By Id", () -> showInfo(textGUI, "Delete customer (not implemented)"));
        Button showAllCustomersButton = new Button("9. Show All Customers", () -> showInfo(textGUI, "Delete customer (not implemented)"));
        Button backButton = new Button("Back", customerWindow::close);

        customerPanel.addComponent(createButton);
        customerPanel.addComponent(deleteButton);
        customerPanel.addComponent(findCustomerByIdButton);
        customerPanel.addComponent(showAllCustomersButton);
        customerPanel.addComponent(backButton);

        customerWindow.setComponent(customerPanel);
        textGUI.addWindowAndWait(customerWindow);
    }

    private static void openLoansMenu(WindowBasedTextGUI textGUI, LoanService loanService) {

    }

    private static void showInfo(WindowBasedTextGUI gui, String message) {
        MessageDialog.showMessageDialog(gui, "Info", message);
    }
}