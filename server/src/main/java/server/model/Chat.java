package server.model;

import shared.datatype.ChatType;

import java.util.LinkedList;

public class Chat {
    private int id;
    private int parentChatId;

    private String chatName;
    private int ownerId;
    private ChatType chatType;
    private LinkedList<Integer> memberIds;
    private LinkedList<Integer> pmIds;
    private int unreadCount;

    public Chat(int ownerId, ChatType chatType) {
        this.id = -1;
        this.parentChatId = -1;

        this.ownerId = ownerId;
        this.chatType = chatType;
        this.memberIds = new LinkedList<>();
        this.pmIds = new LinkedList<>();
        this.unreadCount = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getParentChatId() {
        return parentChatId;
    }

    public void setParentChatId(int parentChatId) {
        this.parentChatId = parentChatId;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public LinkedList<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(LinkedList<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public LinkedList<Integer> getPmIds() {
        return pmIds;
    }

    public void setPmIds(LinkedList<Integer> pmIds) {
        this.pmIds = pmIds;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
