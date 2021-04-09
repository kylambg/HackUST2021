package exception;

public class BadTransactionActionException extends Exception {
    private final String action;

    public BadTransactionActionException(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}

