package se.yrgo.client;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import se.yrgo.domain.Customer;
import se.yrgo.service.CustomerService;

import java.util.List;

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
            Customer newCustomer = new Customer(name.getText(), email.getText());
            customerService.newCustomer(newCustomer);
            createCustomerWindow.close();
        });
        createCustomerWindow.setComponent(createCustomerPanel);
        textGUI.addWindowAndWait(createCustomerWindow);
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
