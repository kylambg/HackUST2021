package exception;

public class InternalServerException extends Exception {
    public InternalServerException(Exception exception) {
        super(exception);
        System.out.println("INTERNAL SERVER EXCEPTION");
    }
}
