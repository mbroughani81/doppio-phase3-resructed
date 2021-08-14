package shared.request;

import shared.response.Response;

public class NewPrivateChatRequest extends Request {

    private int user1Id;
    private int user2Id;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.newPrivateChat(this);
    }

    public NewPrivateChatRequest(int user1Id, int user2Id) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }
}
