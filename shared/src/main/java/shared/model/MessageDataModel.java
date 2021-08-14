package shared.model;

import shared.datatype.Pair;

import java.util.LinkedList;

public class MessageDataModel {
    private int id;

    private LinkedList<Pair<Integer, String>> chatModelIds;

    public MessageDataModel(LinkedList<Pair<Integer, String>> chatModelIds) {
        this.id = -1;
        this.chatModelIds = chatModelIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Pair<Integer, String>> getChatModelIds() {
        return chatModelIds;
    }

    public void setChatModelIds(LinkedList<Pair<Integer, String>> chatModelIds) {
        this.chatModelIds = chatModelIds;
    }
}
