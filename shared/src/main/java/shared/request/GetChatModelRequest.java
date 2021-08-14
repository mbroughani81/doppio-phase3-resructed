package shared.request;

import shared.response.Response;

public class GetChatModelRequest extends Request {

    private int chatId;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.fetchChatModel(this);
    }

    public GetChatModelRequest(int chatId) {
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
