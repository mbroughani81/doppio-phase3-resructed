package shared.model;

public class SingleChat {

    private int chatId;
    private String chatName;
    private int unreadCount;

    public SingleChat(int chatId, String chatName, int unreadCount) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.unreadCount = unreadCount;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
