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

import java.util.List;

import static se.yrgo.client.Utils.isNullOrEmpty;

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
            Book bookByIsbn = bookService.findBookByIsbn(bookIsbn.getText());

            if (bookByIsbn == null) {
                MessageDialog.showMessageDialog(textGUI, "Book Not Found", "No book found with the given ISBN.");
                return;
            }

            Table<String> table = new Table<>("Id", "Title", "Author", "ISBN");
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
                Book existingBook = bookService.findBookByIsbn(bookIsbn.getText());
                if (!isNullOrEmpty(title.getText())) {
                    existingBook.setTitle(title.getText());
                }
                if (!isNullOrEmpty(author.getText())) {
                    existingBook.setAuthor(author.getText());
                }
                if (!isNullOrEmpty(isbn.getText())) {
                    existingBook.setIsbn(isbn.getText());
                }
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

    public static void Delete(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow searchWindow = new BasicWindow("Find Book To Delete");

        Panel searchPanel = new Panel(new GridLayout(1));

        searchPanel.addComponent(new Label("Book ISBN"));
        TextBox bookIsbn = new TextBox();
        searchPanel.addComponent(bookIsbn);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        Button backButton = new Button("Back", searchWindow::close);
        searchPanel.addComponent(backButton);

        findButton.addListener(button -> {
            Book bookByIsbn = bookService.findBookByIsbn(bookIsbn.getText());

            if (bookByIsbn == null) {
                MessageDialog.showMessageDialog(textGUI, "Book Not Found", "No book found with the given ISBN.");
                return;
            }

            Table<String> table = new Table("Title", "Author", "ISBN");
            table.getTableModel().addRow(bookByIsbn.getTitle(), bookByIsbn.getAuthor(), bookByIsbn.getIsbn());

            BasicWindow deleteBookWindow = new BasicWindow("Delete Book");

            Panel deleteBookPanel = new Panel();
            deleteBookPanel.setLayoutManager(new GridLayout(2));

            deleteBookPanel.addComponent(new Label("Title"));
            deleteBookPanel.addComponent(new Label(bookByIsbn.getTitle()));

            deleteBookPanel.addComponent(new Label("Author"));
            deleteBookPanel.addComponent(new Label(bookByIsbn.getAuthor()));

            deleteBookPanel.addComponent(new Label("ISBN"));
            deleteBookPanel.addComponent(new Label(bookByIsbn.getIsbn()));

            Button deleteButton = new Button("Delete", () -> {
                Book bookToDelete = bookService.findBookByIsbn(bookIsbn.getText());
                bookService.deleteBook(bookToDelete);
                deleteBookWindow.close();
                searchWindow.close();
            });
            deleteBookPanel.addComponent(deleteButton);

            Button cancelButton = new Button("Cancel", deleteBookWindow::close);
            deleteBookPanel.addComponent(cancelButton);

            deleteBookWindow.setComponent(deleteBookPanel);
            textGUI.addWindowAndWait(deleteBookWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void findBookByIsbn(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow searchWindow = new BasicWindow("Find Book by ISBN");

        Panel searchPanel = new Panel(new GridLayout(1));

        searchPanel.addComponent(new Label("Book ISBN"));
        TextBox bookIsbn = new TextBox();
        searchPanel.addComponent(bookIsbn);

        Button findButton = new Button("Find");
        searchPanel.addComponent(findButton);

        Button cancelButton = new Button("Cancel", searchWindow::close);
        searchPanel.addComponent(cancelButton);

        findButton.addListener(button -> {
            Book bookByIsbn = bookService.findBookByIsbn(bookIsbn.getText());

            if (bookByIsbn == null) {
                MessageDialog.showMessageDialog(textGUI, "Book Not Found", "No book found with the given ISBN.");
            }

            Table<String> table = new Table<>("Title", "Author", "ISBN");
            table.getTableModel().addRow(bookByIsbn.getTitle(), bookByIsbn.getAuthor(), bookByIsbn.getIsbn());

            BasicWindow foundBookWindow = new BasicWindow("Found Book");

            Panel foundBookPanel = new Panel();
            foundBookPanel.setLayoutManager(new GridLayout(2));

            foundBookPanel.addComponent(new Label("Title"));
            foundBookPanel.addComponent(new Label(bookByIsbn.getTitle()));

            foundBookPanel.addComponent(new Label("Author"));
            foundBookPanel.addComponent(new Label(bookByIsbn.getAuthor()));

            foundBookPanel.addComponent(new Label("ISBN"));
            foundBookPanel.addComponent(new Label(bookByIsbn.getIsbn()));

            Button backButton = new Button("Back", foundBookWindow::close);
            foundBookPanel.addComponent(backButton);

            foundBookWindow.setComponent(foundBookPanel);
            textGUI.addWindowAndWait(foundBookWindow);
        });

        searchWindow.setComponent(searchPanel);
        textGUI.addWindowAndWait(searchWindow);
    }

    public static void showAllBooks(WindowBasedTextGUI textGUI, BookService bookService) {
        BasicWindow allBooksWindow = new BasicWindow("All Books");
        Panel booksPanel = new Panel(new GridLayout(1));

        Table<String> table = new Table<>("Id", "Title", "Author", "ISBN");
        List<Book> allBooks = bookService.getBooks();
        for (Book book : allBooks) {
            table.getTableModel().addRow(String.valueOf(book.getId()), book.getTitle(), book.getAuthor(), book.getIsbn());
        }
        Button backButton = new Button("Back", allBooksWindow::close);

        booksPanel.addComponent(table);

        booksPanel.addComponent(backButton);

        allBooksWindow.setComponent(booksPanel);
        textGUI.addWindowAndWait(allBooksWindow);
    }
}
