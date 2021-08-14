package shared.request;

import shared.response.Response;

public class NewFollowRequest extends Request {

    private int followerId;
    private int followedId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newFollow(this);
    }

    public NewFollowRequest(int followerId, int followedId) {
        this.followerId = followerId;
        this.followedId = followedId;
    }

    public NewFollowRequest(int followedId) {
        this.followerId = -1;
        this.followedId = followedId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowedId() {
        return followedId;
    }

    public void setFollowedId(int followedId) {
        this.followedId = followedId;
    }
}
