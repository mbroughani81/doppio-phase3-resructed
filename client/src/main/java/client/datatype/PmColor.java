package client.datatype;

public enum PmColor {
    OFFLINE("ss1"),
    SENT("ss2"),
    NOTSEEN("ss3"),
    SEEN("ss4");

    private String colorCode;

    PmColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
