package se.yrgo.exceptions;

public class NonExistantCustomerException extends RuntimeException {
    public NonExistantCustomerException() {
        super("Customer does not exist");
    }
}
