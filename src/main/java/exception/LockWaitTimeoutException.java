package exception;

public class LockWaitTimeoutException extends Exception {
    public void message(){
        System.out.println("Lock Timeout");
    }
}
