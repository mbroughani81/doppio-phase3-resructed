package shared.model;

import shared.datatype.ChatType;

import java.util.LinkedList;

public class ChatModel {
    private int id;

    private int chatId;
    private ChatType chatType;
    private LinkedList<SinglePm> pms;

    public ChatModel(int chatId, ChatType chatType, LinkedList<SinglePm> pms) {
        this.id = -1;
        this.chatId = chatId;
        this.chatType = chatType;
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

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public LinkedList<SinglePm> getPms() {
        return pms;
    }

    public void setPms(LinkedList<SinglePm> pms) {
        this.pms = pms;
    }
}
