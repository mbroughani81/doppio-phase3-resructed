package server.model;

import shared.datatype.PmVerdict;

import java.time.LocalDateTime;

public class ScheduledPm {

    private int id;
    private int userId;

    private PmVerdict pmVerdict;
    private String text;
    private LocalDateTime date;

    public ScheduledPm(int userId, PmVerdict pmVerdict, String text, LocalDateTime date) {
        this.id = -1;
        this.userId = userId;
        this.pmVerdict = pmVerdict;
        this.text = text;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
