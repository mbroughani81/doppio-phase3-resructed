package shared.model;

public class AuthToken {
    private final long val;

    public AuthToken(long val) {
        this.val = val;
    }

    public long getVal() {
        return val;
    }
}
