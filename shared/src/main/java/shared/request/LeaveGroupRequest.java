package shared.request;

import shared.response.Response;

public class LeaveGroupRequest extends Request {

    private int chatId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.leaveGroup(this);
    }

    public LeaveGroupRequest(int chatId) {
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
