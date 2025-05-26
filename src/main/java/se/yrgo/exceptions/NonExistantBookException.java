package se.yrgo.exceptions;

public class NonExistantBookException extends RuntimeException {
    public NonExistantBookException() {
        super("Book does not exist");
    }
}
