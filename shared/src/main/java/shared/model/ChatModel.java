package shared.model;

import java.util.LinkedList;

public class ChatModel {
    private int id;

    int chatId;
    private LinkedList<SinglePm> pms;

    public ChatModel(int chatId, LinkedList<SinglePm> pms) {
        this.id = -1;
        this.chatId = chatId;
        this.pms = pms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public LinkedList<SinglePm> getPms() {
        return pms;
    }

    public void setPms(LinkedList<SinglePm> pms) {
        this.pms = pms;
    }
}
