package client.dbcontroller;

import shared.model.MessageDataModel;

import java.util.LinkedList;

public class MessageDataModelController extends AbstractModelController {

    public void updateMessageDataModelDB(MessageDataModel messageDataModel) {
        MessageDataModel oldMessageDataModel = getMessageDataModel();
        if (oldMessageDataModel == null) {
            context.MessageDataModels.add(usernameDir, messageDataModel);
        } else {
            messageDataModel.setId(oldMessageDataModel.getId());
            context.MessageDataModels.update(usernameDir, messageDataModel);
        }
    }
    public MessageDataModel getMessageDataModel() {
        if (context.MessageDataModels.all(usernameDir).size() > 0)
            return context.MessageDataModels.all(usernameDir).get(0);
        return new MessageDataModel(new LinkedList<>());
    }
}
