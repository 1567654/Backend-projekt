package se.yrgo.domain;


import javax.persistence.*;


@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String author;
    private String isbn;

    public Book() {
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public void updateFrom(Book updated) {
        this.title = updated.getTitle();
        this.author = updated.getAuthor();
        this.isbn = updated.getIsbn();
    }

    public String getAuthor() {
        return author;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
