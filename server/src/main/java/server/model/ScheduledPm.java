package server.model;

import java.time.LocalDateTime;

public class ScheduledPm {

    private int id;
    private int userId;
    private int chatId;
    private boolean isSent;
    private String text;
    private LocalDateTime date;

    public ScheduledPm(int userId, int chatId, String text, LocalDateTime date) {
        this.id = -1;
        this.userId = userId;
        this.chatId = chatId;
        this.isSent = false;
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

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
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
