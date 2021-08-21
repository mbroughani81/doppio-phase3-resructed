package client.apps.messenger.view.comp;

import javafx.scene.control.Label;
import shared.model.SingleChat;

public class MessageLabel extends Label {

    private SingleChat singleChat;

    public MessageLabel(String text, SingleChat singleChat) {
        super(text);
        this.singleChat = singleChat;
    }

    public SingleChat getSingleChat() {
        return singleChat;
    }

    public void setSingleChat(SingleChat singleChat) {
        this.singleChat = singleChat;
    }
}
