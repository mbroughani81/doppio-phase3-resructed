package shared.request;

import shared.model.SingleUser;
import shared.response.Response;

import java.util.LinkedList;

public class NewGroupRequest extends Request {

    private int ownerId;
    private String groupname;
    private LinkedList<SingleUser> singleUsers;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newGroup(this);
    }

    public NewGroupRequest(int ownerId, String groupname, LinkedList<SingleUser> singleUsers) {
        this.ownerId = ownerId;
        this.groupname = groupname;
        this.singleUsers = singleUsers;
    }

    public NewGroupRequest(String groupname, LinkedList<SingleUser> singleUsers) {
        this.ownerId = -1;
        this.groupname = groupname;
        this.singleUsers = singleUsers;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public LinkedList<SingleUser> getSingleUsers() {
        return singleUsers;
    }

    public void setSingleUsers(LinkedList<SingleUser> singleUsers) {
        this.singleUsers = singleUsers;
    }
}
