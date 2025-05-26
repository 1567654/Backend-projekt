package se.yrgo.client;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import se.yrgo.domain.Book;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.service.BookService;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;

import java.time.LocalDate;
import java.util.List;

import static se.yrgo.client.Utils.isNullOrEmpty;

public class LoanMenu {
    public static void create(WindowBasedTextGUI textGUI, LoanService loanService, CustomerService customerService, BookService bookService) {
        BasicWindow createLoanWindow = new BasicWindow("Create Loan");

        Panel createLoanPanel = new Panel();
        createLoanPanel.setLayoutManager(new GridLayout(2));

        createLoanPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        createLoanPanel.addComponent(customerEmail);

        createLoanPanel.addComponent(new Label("Book ISBN"));
        TextBox bookIsbn = new TextBox();
        createLoanPanel.addComponent(bookIsbn);

        Button cancelButton = new Button("Cancel", createLoanWindow::close);
        createLoanPanel.addComponent(cancelButton);

        Button createButton = new Button("Create", () -> {
            Customer customer = customerService.findCustomerByEmail(customerEmail.getText());
            Book book = bookService.findBookByIsbn(bookIsbn.getText());

            if (customer == null || book == null) {
                MessageDialog.showMessageDialog(textGUI, "Error", "Customer or Book not found.");
                return;
            }

            for (Loan loan : loanService.getAllLoans()) {
                if (loan.getBook().equals(book)) {
                    MessageDialog.showMessageDialog(textGUI, "Book not available", "Book has already been lent out");
                    return;
                }
            }

            Loan newLoan = new Loan(customer, book, LocalDate.now());
            loanService.loan(newLoan);
            MessageDialog.showMessageDialog(textGUI, "Success", "Loan was successfully created.");
            createLoanWindow.close();
        });
        createLoanPanel.addComponent(createButton);

        createLoanWindow.setComponent(createLoanPanel);
        textGUI.addWindowAndWait(createLoanWindow);
    }

    private static void extendLoanMenu(WindowBasedTextGUI textGUI, LoanService loanService, Loan loan) {
        BasicWindow extendLoanWindow = new BasicWindow("Extend loan with 30 days?");

        Panel extendLoanPanel = new Panel();
        extendLoanPanel.setLayoutManager(new GridLayout(2));

        Button extendButton = new Button("Extend", () -> {
            loanService.extendLoan(loan);
            extendLoanWindow.close();
        });
        extendLoanPanel.addComponent(extendButton);

        Button cancelButton = new Button("Cancel", extendLoanWindow::close);
        extendLoanPanel.addComponent(cancelButton);

        extendLoanWindow.setComponent(extendLoanPanel);
        textGUI.addWindowAndWait(extendLoanWindow);
    }

    public static void extend(WindowBasedTextGUI textGUI, LoanService loanService, CustomerService customerService) {
        BasicWindow searchWindow = new BasicWindow("Find Loan to Extend");

        Panel searchPanel = new Panel(new GridLayout(2));

        searchPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        searchPanel.addComponent(customerEmail);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        findButton.addListener(button -> {
            Customer customer = customerService.findCustomerByEmail(customerEmail.getText());
            if (customer == null) {
                MessageDialog.showMessageDialog(textGUI, "Customer Not Found", "No customer found with the given email.");
                return;
            }
            List<Loan> foundLoans = loanService.findLoansByCustomer(customer);
            if (foundLoans.isEmpty()) {
                MessageDialog.showMessageDialog(textGUI, "No loans found", "No loans for the given email.");
                return;
            }

            ActionListDialogBuilder dialog = new ActionListDialogBuilder().setTitle("Choose a loan to extend").setDescription("CustomerEmail, BookTitle, BookISBN, BorrowDate, BorrowedTill");

            for (Loan loan : foundLoans) {
                String info =
                        loan.getCustomer().getEmail() + " " +
                                loan.getBook().getTitle() + " " +
                                loan.getBook().getIsbn() + " " +
                                loan.getBorrowDate() + " " +
                                loan.getBorrowDate().plusDays(loan.getLendingDays()) + " ";
                dialog.addAction(info, () -> extendLoanMenu(textGUI, loanService, loan));
            }
            dialog.build().showDialog(textGUI);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void zeturn(WindowBasedTextGUI textGUI, LoanService loanService, CustomerService customerService) {
        BasicWindow searchWindow = new BasicWindow("Find Loan to Delete");

        Panel searchPanel = new Panel(new GridLayout(2));

        searchPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        searchPanel.addComponent(customerEmail);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        findButton.addListener(button -> {
            Customer customer = customerService.findCustomerByEmail(customerEmail.getText());
            if (customer == null) {
                MessageDialog.showMessageDialog(textGUI, "Customer Not Found", "No customer found with the given email.");
                return;
            }

            List<Loan> foundLoans = loanService.findLoansByCustomer(customer);
            if (foundLoans == null) {
                MessageDialog.showMessageDialog(textGUI, "No loans found", "No loans for the given email.");
                return;
            }

            BasicWindow customerLoansWindow = new BasicWindow("Loans");

            Panel customerLoansPanel = new Panel();
            customerLoansPanel.setLayoutManager(new GridLayout(1));

            Table<String> table = new Table<>("Id", "Email ", "Title ", "ISBN ", "BorrowDate ", "ReturnDate");

            for (Loan loan : foundLoans) {
                table.getTableModel().addRow(
                        String.valueOf(loan.getId()),
                        loan.getCustomer().getEmail(),
                        loan.getBook().getTitle(),
                        loan.getBook().getIsbn(),
                        loan.getBorrowDate().toString(),
                        loan.getBorrowDate().plusDays(loan.getLendingDays()).toString());
            }

            table.setSelectAction(() -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    List<String> rowData = table.getTableModel().getRow(selectedRow);
                    String idStr = rowData.getFirst();
                    if (isNullOrEmpty(idStr)) {
                        MessageDialog.showMessageDialog(textGUI, "Customer Not Found", "No customer found with the given email.");
                    }
                    int id = Integer.parseInt(idStr);
                    Loan selectedLoan = loanService.getById(id);


                    BasicWindow returnWindow = new BasicWindow("Return Book");

                    Panel returnPanel = new Panel(new GridLayout(1));

                    returnPanel.addComponent(new Label("Are you sure you want to return " + selectedLoan.getBook().getTitle() + "?"));

                    Button yesButton = new Button("Yes", () -> {
                        loanService.zeturn(selectedLoan);
                        returnWindow.close();
                        customerLoansWindow.close();
                    });

                    returnPanel.addComponent(yesButton);

                    Button noButton = new Button("No", returnWindow::close);
                    returnPanel.addComponent(noButton);

                    returnWindow.setComponent(returnPanel);
                    textGUI.addWindowAndWait(returnWindow);

                } else {
                    MessageDialog.showMessageDialog(textGUI, "Customer Not Found", "No customer found with the given email.");
                }
            });

            customerLoansPanel.addComponent(table);


            Button backLoansButton = new Button("Back", customerLoansWindow::close);
            customerLoansPanel.addComponent(backLoansButton);

            customerLoansWindow.setComponent(customerLoansPanel);
            textGUI.addWindowAndWait(customerLoansWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void viewCustomerLoans(WindowBasedTextGUI textGUI, LoanService loanService, CustomerService customerService) {
        BasicWindow searchWindow = new BasicWindow("Find Customer Loans");

        Panel searchPanel = new Panel(new GridLayout(2));

        searchPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        searchPanel.addComponent(customerEmail);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        findButton.addListener(button -> {
            Customer customer = customerService.findCustomerByEmail(customerEmail.getText());
            List<Loan> foundLoans = loanService.findLoansByCustomer(customer);

            if (foundLoans == null) {
                MessageDialog.showMessageDialog(textGUI, "No loans found", "No loans for the given email.");
                return;
            }

            BasicWindow customerLoansWindow = new BasicWindow("Loans");

            Panel customerLoansPanel = new Panel();
            customerLoansPanel.setLayoutManager(new GridLayout(1));

            Table<String> table = new Table<>("Email ", "Title ", "ISBN ", "BorrowDate ", "ReturnDate");

            for (Loan loan : foundLoans) {
                table.getTableModel().addRow(
                        loan.getCustomer().getEmail(),
                        loan.getBook().getTitle(),
                        loan.getBook().getIsbn(),
                        loan.getBorrowDate().toString(),
                        loan.getBorrowDate().plusDays(loan.getLendingDays()).toString());
            }

            customerLoansPanel.addComponent(table);

            Button backLoansButton = new Button("Back", customerLoansWindow::close);
            customerLoansPanel.addComponent(backLoansButton);

            customerLoansWindow.setComponent(customerLoansPanel);
            textGUI.addWindowAndWait(customerLoansWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }
}
