package Exceptions;

public class WebException extends RuntimeException {

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

}
