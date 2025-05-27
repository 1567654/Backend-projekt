package se.yrgo.client;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import se.yrgo.domain.Customer;
import se.yrgo.domain.Loan;
import se.yrgo.service.CustomerService;
import se.yrgo.service.LoanService;
import java.util.List;

import static se.yrgo.client.Utils.isNullOrEmpty;

public class CustomerMenu {
    public static void Create(WindowBasedTextGUI textGUI, CustomerService customerService) {
        BasicWindow createCustomerWindow = new BasicWindow("Create Customer");

        Panel createCustomerPanel = new Panel();
        createCustomerPanel.setLayoutManager(new GridLayout(2));

        createCustomerPanel.addComponent(new Label("Name"));
        TextBox name = new TextBox();
        createCustomerPanel.addComponent(name);

        createCustomerPanel.addComponent(new Label("Email"));
        TextBox email = new TextBox();
        createCustomerPanel.addComponent(email);

        createCustomerPanel.addComponent(new EmptySpace(new TerminalSize(10, 10)));

        Button createButton = new Button("Create");
        createCustomerPanel.addComponent(createButton);

        Button cancelButton = new Button("Cancel", createCustomerWindow::close);
        createCustomerPanel.addComponent(cancelButton);

        createButton.addListener(button -> {
            String enteredEmail = email.getText();
            String enteredName = name.getText();
            if (isNullOrEmpty(enteredName)) {
                MessageDialog.showMessageDialog(textGUI, "Invalid input", "Name cannot be empty");
                return;
            }
            if (isNullOrEmpty(enteredEmail) || !enteredEmail.contains("@")) {
                MessageDialog.showMessageDialog(textGUI, "Invalid email", "Email must contain '@'");
                return;
            }
            Customer newCustomer = new Customer(enteredName, enteredEmail);
            customerService.newCustomer(newCustomer);
            createCustomerWindow.close();
        });
        createCustomerWindow.setComponent(createCustomerPanel);
        textGUI.addWindowAndWait(createCustomerWindow);
    }

    public static void Update(WindowBasedTextGUI textGUI, CustomerService customerService) {
        BasicWindow searchWindow = new BasicWindow("Find Customer to Update");

        Panel searchPanel = new Panel(new GridLayout(2));

        searchPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        searchPanel.addComponent(customerEmail);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        findButton.addListener(button -> {
            Customer foundCustomer = null;
            for (Customer c : customerService.getCustomers()) {
                if (c.getEmail().equals(customerEmail.getText())) {
                    foundCustomer = c;
                }
            }

            if (foundCustomer == null) {
                MessageDialog.showMessageDialog(textGUI, "Not Found", "No customer found with the given email.");
                return;
            }

            Table<String> table = new Table<>("ID", "Name", "Email");
            table.getTableModel().addRow(String.valueOf(foundCustomer), foundCustomer.getName(), foundCustomer.getEmail());

            BasicWindow updateCustomerWindow = new BasicWindow("Update Customer");

            Panel updateCustomerPanel = new Panel();
            updateCustomerPanel.setLayoutManager(new GridLayout(2));

            updateCustomerPanel.addComponent(new Label("Current Name:"));
            updateCustomerPanel.addComponent(new Label(foundCustomer.getName()));

            updateCustomerPanel.addComponent(new Label("New Name"));
            TextBox name = new TextBox();
            updateCustomerPanel.addComponent(name);


            updateCustomerPanel.addComponent(new Label("Current Email:"));
            updateCustomerPanel.addComponent(new Label(foundCustomer.getEmail()));

            updateCustomerPanel.addComponent(new Label("New Email"));
            TextBox email = new TextBox();
            updateCustomerPanel.addComponent(email);

            updateCustomerPanel.addComponent(new EmptySpace(new TerminalSize(10, 10)));

            Customer finalFoundCustomer = foundCustomer;
            Button updateButton = new Button("Update", () -> {
                Customer updatedCustomer = null;
                for (Customer c : customerService.getCustomers()) {
                    if (c.getEmail().equals(finalFoundCustomer.getEmail())) {
                        updatedCustomer = c;
                    }
                }
                if (updatedCustomer == null) {
                    MessageDialog.showMessageDialog(textGUI, "Customer Not Found", "No customer found with given email.");
                    return;
                }
                if (!isNullOrEmpty(name.getText())) {
                    updatedCustomer.setName(name.getText());
                }
                if (!isNullOrEmpty(email.getText())) {
                    updatedCustomer.setEmail(email.getText());
                }
                customerService.updateCustomer(updatedCustomer);
                updateCustomerWindow.close();
                searchWindow.close();
            });
            updateCustomerPanel.addComponent(updateButton);

            Button cancelButton = new Button("Cancel", updateCustomerWindow::close);
            updateCustomerPanel.addComponent(cancelButton);

            updateCustomerWindow.setComponent(updateCustomerPanel);
            textGUI.addWindowAndWait(updateCustomerWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void Delete(WindowBasedTextGUI textGUI, CustomerService customerService, LoanService loanService) {
        BasicWindow searchWindow = new BasicWindow("Find Customer to Delete");

        Panel searchPanel = new Panel(new GridLayout(1));

        searchPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        searchPanel.addComponent(customerEmail);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        findButton.addListener(button -> {
            Customer customerByEmail = null;
            for (Customer c : customerService.getCustomers()) {
                if (c.getEmail().equals(customerEmail.getText())) {
                    customerByEmail = c;
                }
            }

            if (customerByEmail == null) {
                MessageDialog.showMessageDialog(textGUI, "Customer not found", "No customer found with the given email");
                return;
            }

            Table<String> table = new Table<>("Name", "Email");
            table.getTableModel().addRow(customerByEmail.getName(), customerByEmail.getEmail());

            BasicWindow deleteCustomerWindow = new BasicWindow("Delete Customer");

            Panel deleteCustomerPanel = new Panel();
            deleteCustomerPanel.setLayoutManager(new GridLayout(2));

            deleteCustomerPanel.addComponent(new Label("Name"));
            deleteCustomerPanel.addComponent(new Label(customerByEmail.getName()));

            deleteCustomerPanel.addComponent(new Label("Email"));
            deleteCustomerPanel.addComponent(new Label(customerByEmail.getEmail()));

            Button deleteButton = new Button("Delete", () -> {
                Customer customerToDelete = null;
                for (Customer c : customerService.getCustomers()) {
                    if (c.getEmail().equals(customerEmail.getText())) {
                        customerToDelete = c;
                    }
                }

                List<Loan> loans = loanService.findLoansByCustomer(customerToDelete);
                if (loans != null && !loans.isEmpty()) {
                    loanService.returnAllLoansForCustomer(customerToDelete);
                }

                customerService.deleteCustomer(customerToDelete);
                deleteCustomerWindow.close();
                searchWindow.close();
            });
            deleteCustomerPanel.addComponent(deleteButton);

            Button cancelButton = new Button("Cancel", deleteCustomerWindow::close);
            deleteCustomerPanel.addComponent(cancelButton);

            deleteCustomerWindow.setComponent(deleteCustomerPanel);
            textGUI.addWindowAndWait(deleteCustomerWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void FindByEmail(WindowBasedTextGUI textGUI, CustomerService customerService) {
        BasicWindow searchWindow = new BasicWindow("Find Customer by Email");

        Panel searchPanel = new Panel(new GridLayout(1));

        searchPanel.addComponent(new Label("Customer Email"));
        TextBox customerEmail = new TextBox();
        searchPanel.addComponent(customerEmail);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        findButton.addListener(button -> {
            Customer customerByEmail = null;
            for (Customer c : customerService.getCustomers()) {
                if (c.getEmail().equals(customerEmail.getText())) {
                    customerByEmail = c;
                }
            }

            if (customerByEmail == null) {
                MessageDialog.showMessageDialog(textGUI, "Customer not found", "No customer found with the given email");
                return;
            }

            Table<String> table = new Table<>("Id", "Name", "Email");
            table.getTableModel().addRow(String.valueOf(customerByEmail.getId()), customerByEmail.getName(), customerByEmail.getEmail());

            BasicWindow displayCustomerWindow = new BasicWindow("Found Customer");

            Panel displayCustomerPanel = new Panel();
            displayCustomerPanel.setLayoutManager(new GridLayout(2));

            displayCustomerPanel.addComponent(new Label("Id"));
            displayCustomerPanel.addComponent(new Label(String.valueOf(customerByEmail.getId())));

            displayCustomerPanel.addComponent(new Label("Name"));
            displayCustomerPanel.addComponent(new Label(customerByEmail.getName()));

            displayCustomerPanel.addComponent(new Label("Email"));
            displayCustomerPanel.addComponent(new Label(customerByEmail.getEmail()));

            Button closeButton = new Button("Close", displayCustomerWindow::close);
            displayCustomerPanel.addComponent(closeButton);

            displayCustomerWindow.setComponent(displayCustomerPanel);
            textGUI.addWindowAndWait(displayCustomerWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void showAllCustomers(WindowBasedTextGUI textGUI, CustomerService customerService) {
        BasicWindow allCustomersWindow = new BasicWindow("All Customers");
        Panel customersPanel = new Panel(new GridLayout(1));

        Table<String> table = new Table<>("Id", "Name", "Email");
        List<Customer> allCustomers = customerService.getCustomers();
        for (Customer customer : allCustomers) {
            table.getTableModel().addRow(String.valueOf(customer.getId()), customer.getName(), customer.getEmail());
        }
        Button backButton = new Button("Back", allCustomersWindow::close);

        customersPanel.addComponent(table);

        customersPanel.addComponent(backButton);

        allCustomersWindow.setComponent(customersPanel);
        textGUI.addWindowAndWait(allCustomersWindow);
    }
}
