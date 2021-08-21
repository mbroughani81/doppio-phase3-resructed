package client.apps.chat.view;

import client.apps.chat.view.comp.HypSinglePmLabelController;
import client.config.apps.chat.ChatRootConfig;
import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import client.dbcontroller.ChatModelController;
import client.utils.ChatModelUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import shared.datatype.ChatType;
import shared.model.ChatModel;
import shared.model.SinglePm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import shared.request.LeaveGroupRequest;
import shared.request.NewPmRequest;
import shared.request.NewScheduledPmRequest;
import shared.util.ImageSerializer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ChatRootController extends BasicController implements Initializable {

    ChatModelController chatModelController = DoppioApp.getChatModelController();
    int chatId;
    ChatModel curChatModel;
    File selectedFile;

    @FXML
    private Button leavegroupButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox pmHolder;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private CheckBox scheduledCheckBox;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField scheduledhourTextField;

    @FXML
    private TextField scheduledminuteTextField;

    @FXML
    private Button sendButton;

    @FXML
    private Button selectImageButton;

    @FXML
    void leavegroupButtonClicked(ActionEvent event) {
        getListener().listen(new LeaveGroupRequest(chatId));
        ViewSwitcher.getInstance().switchTo(new Page(View.MESSENGER, -1));
    }


    @FXML
    void selectImageButtonClicked(ActionEvent event) {
        ChatRootConfig config = new ChatRootConfig();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        config.getFilechooserDescription(),
                        config.getFilechooserFormat()
                )
        );
        selectedFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());
    }

    @FXML
    void sendButtonClicked(ActionEvent event) {
        if (messageTextArea.getText().length() == 0)
            return;
        if (scheduledCheckBox.isSelected()) {
            LocalDateTime date = LocalDateTime.of(
                    dateField.getValue().getYear(),
                    dateField.getValue().getMonth(),
                    dateField.getValue().getDayOfMonth(),
                    Integer.parseInt(scheduledhourTextField.getText()),
                    Integer.parseInt(scheduledminuteTextField.getText())
            );
            getListener().listen(new NewScheduledPmRequest(chatId, messageTextArea.getText(), date));
        } else {
            NewPmRequest request = new NewPmRequest(
                    chatId,
                    messageTextArea.getText(),
                    (selectedFile != null) ? ImageSerializer.encodeFileToBase64Binary(selectedFile) : null
            );
            getListener().listen(request);
            chatModelController.sendNewPm(request);
        }
        messageTextArea.setText("");
        selectedFile = null;
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            ChatRootConfig config = new ChatRootConfig();
            ChatModel newChatModel = DoppioApp.getChatModelController().getChatModel(chatId);
            //
            if (newChatModel.getChatType() == ChatType.GROUP && !leavegroupButton.isVisible()) {
                leavegroupButton.setVisible(true);
            }

            //
            if (ChatModelUtility.isChatModelChanged(curChatModel, newChatModel)) {
                curChatModel = newChatModel;
                pmHolder.getChildren().clear();
                this.clearChildControllers();
                for (SinglePm pm : curChatModel.getPms()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(HypSinglePmLabelController.class.getResource(config.getHypersinglelabelFxmlFilename()));
                    HypSinglePmLabelController singlePmLabelController = new HypSinglePmLabelController(pm);
                    this.addToChildControllers(singlePmLabelController);
                    fxmlLoader.setController(singlePmLabelController);
                    try {
                        pmHolder.getChildren().add(fxmlLoader.load());;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            runChildControllerUpdate();
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

        addTextLimiter(scheduledhourTextField, 2);
        addTextLimiter(scheduledminuteTextField, 2);

        dateField.setValue(LocalDate.now());
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
}
