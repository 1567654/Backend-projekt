package se.yrgo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.domain.Book;
import se.yrgo.exceptions.NonExistantBookException;
import se.yrgo.service.BookService;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Transactional
@ContextConfiguration( {"/applicationTest.xml", "/datasource-test.xml" })
public class BookTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testCreateBook() {
        Book book = new Book("Java", "Jack", "123456789");
        bookService.newBook(book);

        assertEquals(1, bookService.getBooks().size());
    }

    @Test
    public void testNonExistingBook() {
        assertThrows(NonExistantBookException.class, () -> bookService.findBookByIsbn("2785628"));
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book("Java", "Jack", "123456789");
        bookService.newBook(book);

        bookService.deleteBook(book);
        assertEquals(0, bookService.getBooks().size());
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book("Java", "Jack", "123456789");
        bookService.newBook(book);

        book.setAuthor("James");
        bookService.updateBook(book);
        assertEquals("James", bookService.getBooksByAuthor("James").getFirst().getAuthor());
    }

    @Test
    public void testFindBookByIsbn() {
        Book book = new Book("Java", "Jack", "123456789");
        bookService.newBook(book);

        try {
            assertEquals(book, bookService.findBookByIsbn("123456789"));
        }catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindBookByTitle() {
        Book book = new Book("Java", "Jack", "123456789");
        bookService.newBook(book);

        for (Book b : bookService.getBooksByTitle("Java")) {
            assertEquals(book.getTitle(), b.getTitle());
        }
    }

    @Test
    public void testFindBookByAuthor() {
        Book book = new Book("Java", "Jack", "123456789");
        bookService.newBook(book);

        for (Book b : bookService.getBooksByAuthor("Jack")) {
            assertEquals(book.getAuthor(), b.getAuthor());
        }
    }
}
