package client.apps.messenger.view.comp;

import javafx.scene.control.Label;

public class MessageLabel extends Label {
    private int chatId;

    public MessageLabel(String text, int chatId) {
        super(text);
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
