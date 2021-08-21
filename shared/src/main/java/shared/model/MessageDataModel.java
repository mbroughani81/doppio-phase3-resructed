package shared.model;

import java.util.LinkedList;

public class MessageDataModel {
    private int id;

    private LinkedList<SingleChat> singleChats;

    public MessageDataModel(LinkedList<SingleChat> singleChats) {
        this.id = -1;
        this.singleChats = singleChats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<SingleChat> getSingleChats() {
        return singleChats;
    }

    public void setSingleChats(LinkedList<SingleChat> singleChats) {
        this.singleChats = singleChats;
    }
}
