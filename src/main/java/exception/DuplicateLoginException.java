package exception;

public class DuplicateLoginException extends Exception {
    private final String username;

    public DuplicateLoginException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
