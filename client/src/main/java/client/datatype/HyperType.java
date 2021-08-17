package client.datatype;

public enum HyperType {
    CHAT("chat"),
    TWEET("tweet"),
    JOINGROUP("join"),
    UNDEFINED("");

    private String val;

    HyperType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}

