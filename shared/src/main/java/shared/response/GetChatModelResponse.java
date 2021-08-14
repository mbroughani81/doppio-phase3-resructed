package shared.response;

import shared.model.ChatModel;

public class GetChatModelResponse implements Response {

    private ChatModel chatModel;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateChatModelDB(this);
    }

    public GetChatModelResponse(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public void setChatModel(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
}
