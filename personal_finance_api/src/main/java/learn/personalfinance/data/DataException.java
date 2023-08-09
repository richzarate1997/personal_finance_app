package learn.personalfinance.data;

public class DataException extends Exception {
    public DataException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
}