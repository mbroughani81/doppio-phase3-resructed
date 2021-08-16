package shared.request;

import shared.response.Response;

import java.time.LocalDateTime;

public class NewScheduledPmRequest extends Request {

    private int chatId;
    private String text;
    private LocalDateTime date;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newScheduledPm(this);
    }

    public NewScheduledPmRequest(int chatId, String text, LocalDateTime date) {
        this.chatId = chatId;
        this.text = text;
        this.date = date;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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
