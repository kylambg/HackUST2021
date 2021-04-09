package exception;

public class BadTransactionIdException extends Exception {
    private final int transactionId;

    public BadTransactionIdException(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionId() {
        return transactionId;
    }

}

