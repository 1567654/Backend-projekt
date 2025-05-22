package se.yrgo.client;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import se.yrgo.domain.Book;
import se.yrgo.service.BookService;

public class BookMenu {
    public static void Create(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow createBookWindow = new BasicWindow("Create Book");

        Panel createBookPanel = new Panel();
        createBookPanel.setLayoutManager(new GridLayout(2));

        createBookPanel.addComponent(new Label("Title"));
        TextBox title = new TextBox();
        createBookPanel.addComponent(title);

        createBookPanel.addComponent(new Label("Author"));
        TextBox author = new TextBox();
        createBookPanel.addComponent(author);

        createBookPanel.addComponent(new Label("ISBN"));
        TextBox isbn = new TextBox();
        createBookPanel.addComponent(isbn);

        createBookPanel.addComponent(new EmptySpace(new TerminalSize(10, 10)));

        Button create = new Button("Create");
        createBookPanel.addComponent(create);

        Button backButton = new Button("Back", createBookWindow::close);
        createBookPanel.addComponent(backButton);

        //skapa listener, när klickad på kör -> bookservice new book, createBookWindow::close

        create.addListener(button -> {
            Book newBook = new Book(title.getText(), author.getText(), isbn.getText());
            bookService.newBook(newBook);
            createBookWindow.close();
        });
        createBookWindow.setComponent(createBookPanel);
        textGUI.addWindowAndWait(createBookWindow);
    }

    public static void Update(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow searchWindow = new BasicWindow("Find Book To Update");

        Panel searchPanel = new Panel(new GridLayout(1));

        searchPanel.addComponent(new Label("Book ISBN"));
        TextBox bookIsbn = new TextBox();
        searchPanel.addComponent(bookIsbn);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        findButton.addListener(button -> {
            Book bookByIsbn = bookService.getBookByIsbn(bookIsbn.getText());

            if (bookByIsbn == null) {
                MessageDialog.showMessageDialog(textGUI, "Book Not Found", "No book found with the given ISBN.");
                return;
            }

            Table<String> table = new Table<String>("Id", "Title", "Author", "ISBN");
            table.getTableModel().addRow(String.valueOf(bookByIsbn.getId()), bookByIsbn.getTitle(), bookByIsbn.getAuthor(), bookByIsbn.getIsbn());

            BasicWindow updateBookWindow = new BasicWindow("Update Book");

            Panel updateBookPanel = new Panel();
            updateBookPanel.setLayoutManager(new GridLayout(2));


            updateBookPanel.addComponent(new Label("Current Title:"));
            updateBookPanel.addComponent(new Label(bookByIsbn.getTitle()));

            updateBookPanel.addComponent(new Label("New Title"));
            TextBox title = new TextBox();
            updateBookPanel.addComponent(title);


            updateBookPanel.addComponent(new Label("Current Author:"));
            updateBookPanel.addComponent(new Label(bookByIsbn.getAuthor()));

            updateBookPanel.addComponent(new Label("New Author"));
            TextBox author = new TextBox();
            updateBookPanel.addComponent(author);


            updateBookPanel.addComponent(new Label("Current ISBN:"));
            updateBookPanel.addComponent(new Label(bookByIsbn.getIsbn()));

            updateBookPanel.addComponent(new Label("New ISBN"));
            TextBox isbn = new TextBox();
            updateBookPanel.addComponent(isbn);


            updateBookPanel.addComponent(new EmptySpace(new TerminalSize(10, 10)));

            Button updateButton = new Button("Update", () -> {
                Book existingBook = bookService.getBookByIsbn(bookIsbn.getText());
                existingBook.updateFrom(new Book(title.getText(), author.getText(), isbn.getText()));
                bookService.updateBook(existingBook);
                updateBookWindow.close();
                searchWindow.close();
            });
            updateBookPanel.addComponent(updateButton);

            Button cancelButton = new Button("Cancel", updateBookWindow::close);
            updateBookPanel.addComponent(cancelButton);

            updateBookWindow.setComponent(updateBookPanel);
            textGUI.addWindowAndWait(updateBookWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);

    }
}
