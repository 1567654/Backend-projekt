package se.yrgo.client;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Arrays;

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
        }
    }

    private static Panel getPanel(WindowBasedTextGUI textGUI, BasicWindow window, ClassPathXmlApplicationContext container) {
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button customerButton = new Button("1. Customers", () -> openCustomerMenu(textGUI));
        Button libraryButton = new Button("2. Library", () -> showInfo(textGUI, "Library module opened (not implemented)."));
        Button loanButton = new Button("3. Loans", () -> showInfo(textGUI, "Loans module opened (not implemented)."));
        Button exitButton = new Button("Exit", () -> window.close());

        panel.addComponent(customerButton);
        panel.addComponent(libraryButton);
        panel.addComponent(loanButton);
        panel.addComponent(exitButton);
        return panel;
    }

    private static void openCustomerMenu(WindowBasedTextGUI textGUI) {
        BasicWindow customerWindow = new BasicWindow("Customers");

        Panel customerPanel = new Panel(new GridLayout(1));

        Button createButton = new Button("8. Create Customer", () -> showInfo(textGUI, "Create customer (not implemented)"));
        Button deleteButton = new Button("9. Delete Customer", () -> showInfo(textGUI, "Delete customer (not implemented)"));
        Button backButton = new Button("Back", customerWindow::close);

        customerPanel.addComponent(createButton);
        customerPanel.addComponent(deleteButton);
        customerPanel.addComponent(backButton);

        customerWindow.setComponent(customerPanel);
        textGUI.addWindowAndWait(customerWindow);
    }

    private static void showInfo(WindowBasedTextGUI gui, String message) {
        MessageDialog.showMessageDialog(gui, "Info", message);
    }
}