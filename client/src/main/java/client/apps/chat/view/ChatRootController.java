package client.apps.chat.view;

import client.apps.chat.view.comp.HypSinglePmLabelController;
import client.apps.chat.view.comp.SinglePmLabelController;
import client.config.apps.chat.ChatRootConfig;
import client.core.DoppioApp;
import client.datatype.BasicController;
import client.dbcontroller.ChatModelController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import shared.model.SinglePm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import shared.request.NewPmRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatRootController extends BasicController implements Initializable {

    ChatModelController chatModelController = DoppioApp.getChatModelController();
    int chatId;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox pmHolder;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Button sendButton;

    @FXML
    void sendButtonClicked(ActionEvent event) {
        getListener().listen(new NewPmRequest(chatId, messageTextArea.getText()));
        chatModelController.sendNewPm(new NewPmRequest(chatId, messageTextArea.getText()));
        messageTextArea.setText("");
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            pmHolder.getChildren().clear();
            this.clearChildControllers();
            for (SinglePm pm : DoppioApp.getChatModelController().getChatModel(chatId).getPms()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(SinglePmLabelController.class.getResource("hypsinglepmlabel.fxml"));
                HypSinglePmLabelController singlePmLabelController = new HypSinglePmLabelController(pm);
                fxmlLoader.setController(singlePmLabelController);
                //                singlePmLabelController.setListener(getListener());
                try {
                    pmHolder.getChildren().add(fxmlLoader.load());
                    this.addToChildControllers(singlePmLabelController);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            runChildControllerRequest();
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChatRootConfig chatRootConfig = new ChatRootConfig();
        sendButton.setText(chatRootConfig.getSendButtonText());
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
