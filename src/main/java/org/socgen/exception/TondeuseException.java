package org.socgen.exception;

public class TondeuseException extends RuntimeException {
    public TondeuseException() {
        super();
    }

    public TondeuseException(String message) {
        super(message);
    }

    public TondeuseException(Throwable cause) {
        super(cause);
    }
}
