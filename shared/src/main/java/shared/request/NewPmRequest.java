package shared.request;

import shared.response.Response;

public class NewPmRequest extends Request {

    private int chatId;
    private String text;
    private String imageString;
    private int userId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newPm(this);
    }

    public NewPmRequest(int chatId, String text, String imageString, int userId) {
        this.chatId = chatId;
        this.text = text;
        this.imageString = imageString;
        this.userId = userId;
    }

    public NewPmRequest(int chatId, String text, String imageString) {
        this.chatId = chatId;
        this.text = text;
        this.imageString = imageString;
        this.userId = -1;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
