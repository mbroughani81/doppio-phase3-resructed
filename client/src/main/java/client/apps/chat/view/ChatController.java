package client.apps.chat.view;

import client.apps.mainpage.view.MainPageController;
import javafx.application.Platform;
import javafx.scene.Parent;
import shared.request.GetChatModelRequest;

public class ChatController extends MainPageController {

    ChatRootController chatRootController;
    int chatId;

    public ChatController(int chatId) {
        this.chatId = chatId;
    }

    public void setChatRoot(Parent root, ChatRootController chatRootController) {
        setCenter(root);
        chatRootController.setChatId(chatId);
        this.chatRootController = chatRootController;
        this.addToChildControllers(chatRootController);
//        this.chatRootController.setListener(getListener());
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            Platform.runLater(
                    this::runChildControllerUpdate
            );
        };
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            getListener().listen(new GetChatModelRequest(chatId));
            runChildControllerRequest();
        };
    }
}
