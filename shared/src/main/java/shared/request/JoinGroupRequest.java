package shared.request;

import shared.response.Response;

public class JoinGroupRequest extends Request {

    private int chatId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.joinGroup(this);
    }

    public JoinGroupRequest(int chatId) {
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
