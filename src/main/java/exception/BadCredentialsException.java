package exception;

public class BadCredentialsException extends Exception {
    private final String username;

    public BadCredentialsException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
