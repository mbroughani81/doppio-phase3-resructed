package server.model;

import java.util.LinkedList;

public class MessageData {
    private int id;

    private LinkedList<Integer> chatIds;

    public MessageData() {
        this.id = -1;
        this.chatIds = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Integer> getChatIds() {
        return chatIds;
    }

    public void setChatIds(LinkedList<Integer> chatIds) {
        this.chatIds = chatIds;
    }
}
