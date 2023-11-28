package nextstep.courses.exception;

public class DifferentSessionAmountException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DifferentSessionAmountException(String message) {
        super(message);
    }
}