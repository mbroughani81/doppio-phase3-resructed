package shared.response;

import shared.model.MessageDataModel;

public class GetMessageDataModelResponse implements Response {

    private MessageDataModel messageDataModel;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.updateMessageDataModelDB(this);
    }

    public GetMessageDataModelResponse(MessageDataModel messageDataModel) {
        this.messageDataModel = messageDataModel;
    }

    public MessageDataModel getMessageDataModel() {
        return messageDataModel;
    }

    public void setMessageDataModel(MessageDataModel messageDataModel) {
        this.messageDataModel = messageDataModel;
    }
}
