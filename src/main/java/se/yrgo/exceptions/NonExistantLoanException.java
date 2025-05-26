package se.yrgo.exceptions;

public class NonExistantLoanException extends RuntimeException {
    public NonExistantLoanException() {
        super("Loan does not exist");
    }
}
