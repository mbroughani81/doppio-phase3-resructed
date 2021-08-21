package client.dbcontroller;

import client.core.DoppioApp;
import org.apache.logging.log4j.LogManager;
import shared.datatype.ChatType;
import shared.datatype.PmVerdict;
import shared.model.ChatModel;
import shared.model.SinglePm;
import shared.request.NewPmRequest;

import java.util.LinkedList;

public class ChatModelController extends AbstractModelController {

    public void sendNewPm(NewPmRequest newPmRequest) {
        ChatModel chatModel = getChatModel(newPmRequest.getChatId());
        chatModel.getPms().add(new SinglePm(
                -1,
                DoppioApp.getSessionModelController().getSession().getUserId(),
                PmVerdict.OFFLINE,
                newPmRequest.getText()
        ));
        updateChatModelDB(chatModel);

        LogManager.getLogger(ChatModelController.class).info("new pm is added to chatmodel with id" + chatModel.getChatId());
    }

    public void updateChatModelDB(ChatModel chatModel) {
        for (ChatModel chatModel1 : context.ChatModels.all(usernameDir)) {
            if (chatModel1.getChatId() == chatModel.getChatId()) {
                chatModel.setId(chatModel1.getId());
                context.ChatModels.update(usernameDir, chatModel);
                return;
            }
        }
        context.ChatModels.add(usernameDir, chatModel);
    }

    public ChatModel getChatModel(int chatId) {

        for (ChatModel chatModel : context.ChatModels.all(usernameDir)) {
            if (chatModel.getChatId() == chatId)
                return chatModel;
        }
        return new ChatModel(chatId, ChatType.PRIVATE, new LinkedList<>());
    }
}
