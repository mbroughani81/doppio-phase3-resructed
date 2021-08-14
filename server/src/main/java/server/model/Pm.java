package server.model;

import shared.datatype.PmVerdict;

public class Pm {
    private int id;
    private int userId;

    private PmVerdict pmVerdict;
    private String text;

    public Pm(int userId, PmVerdict pmVerdict, String text) {
        this.id = -1;
        this.pmVerdict = pmVerdict;
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

    public PmVerdict getPmVerdict() {
        return pmVerdict;
    }

    public void setPmVerdict(PmVerdict pmVerdict) {
        this.pmVerdict = pmVerdict;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
