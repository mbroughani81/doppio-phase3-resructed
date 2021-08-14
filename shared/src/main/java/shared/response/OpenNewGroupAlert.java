package shared.response;

import shared.model.SingleUser;

import java.util.LinkedList;

public class OpenNewGroupAlert implements Response {

    private LinkedList<SingleUser> availableUsers;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.openNewGroupAlert(this);
    }

    public OpenNewGroupAlert(LinkedList<SingleUser> availableUsers) {
        this.availableUsers = availableUsers;
    }

    public LinkedList<SingleUser> getAvailableUsers() {
        return availableUsers;
    }

    public void setAvailableUsers(LinkedList<SingleUser> availableUsers) {
        this.availableUsers = availableUsers;
    }
}
