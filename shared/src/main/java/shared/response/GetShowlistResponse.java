package shared.response;

import shared.model.SingleUser;

import java.util.LinkedList;

public class GetShowlistResponse implements Response {

    private LinkedList<SingleUser> followings;
    private LinkedList<SingleUser> followers;
    private LinkedList<SingleUser> blacklist;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateShowlistPage(this);
    }

    public GetShowlistResponse(LinkedList<SingleUser> followings, LinkedList<SingleUser> followers, LinkedList<SingleUser> blacklist) {
        this.followings = followings;
        this.followers = followers;
        this.blacklist = blacklist;
    }

    public LinkedList<SingleUser> getFollowings() {
        return followings;
    }

    public void setFollowings(LinkedList<SingleUser> followings) {
        this.followings = followings;
    }

    public LinkedList<SingleUser> getFollowers() {
        return followers;
    }

    public void setFollowers(LinkedList<SingleUser> followers) {
        this.followers = followers;
    }

    public LinkedList<SingleUser> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(LinkedList<SingleUser> blacklist) {
        this.blacklist = blacklist;
    }
}
