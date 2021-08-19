package server.model;

import shared.datatype.PmVerdict;

public class Pm {
    private int id;

    private int userId;
    private String text;

    public Pm(int userId, String text) {
        this.id = -1;
        this.userId = userId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
