package client.apps.messenger.view;

import client.apps.messenger.view.comp.GetGroupMembersController;
import client.apps.messenger.view.comp.GetGroupnameController;
import client.apps.messenger.view.comp.MessageLabel;
import client.config.apps.messenger.MessengerRootConfig;
import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import shared.datatype.Pair;
import client.datatype.View;
import shared.model.MessageDataModel;
import shared.model.SingleChat;
import shared.model.SingleUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import shared.request.GetMultipmAlertData;
import shared.request.GetNewGroupAlertData;
import shared.request.GetNewTypeAlertData;
import shared.request.NewGroupRequest;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MessengerRootController extends BasicController implements Initializable, ChangeListener<MessageLabel> {

    @FXML
    private Label testLabel;

    @FXML
    private ListView<MessageLabel> listView;

    @FXML
    private Button newgroupButton;

    @FXML
    void newgroupButtonClicked(ActionEvent event) {
        getListener().listen(new GetNewGroupAlertData());
    }


    @Override
    public void changed(ObservableValue<? extends MessageLabel> observable, MessageLabel oldValue, MessageLabel newValue) {
        if (listView.getSelectionModel().getSelectedItem() == null)
            return;
        int id = listView.getSelectionModel().getSelectedItem().getSingleChat().getChatId();
        ViewSwitcher.getInstance().switchTo(new Page(View.CHAT, id));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.getSelectionModel().selectedItemProperty().addListener(this);

        MessengerRootConfig messengerRootConfig = new MessengerRootConfig();
        newgroupButton.setText(messengerRootConfig.getNewgroupButtonText());
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            MessageDataModel messageDataModel = DoppioApp.getMessageDataModelController().getMessageDataModel();
            LinkedList<MessageLabel> newItems = new LinkedList<>();
            for (SingleChat singleChat : messageDataModel.getSingleChats()) {
                newItems.add(new MessageLabel(
                        singleChat.getChatId() + " " + singleChat.getChatName() + " -> " + singleChat.getUnreadCount(),
                        singleChat
                ));
            }
            if (!isChanged(listView.getItems(), newItems)) {
                return;
            }
            listView.getItems().clear();
            listView.getItems().addAll(newItems);
        };
    }

    public void openNewGroupAlert(LinkedList<SingleUser> availableUsers) {
        MessengerRootConfig config = new MessengerRootConfig();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(config.getNewgroupgetgroupnameheadertext());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GetGroupnameController.class.getResource(config.getGetgroupnameFxmlFilename()));
        GetGroupnameController getGroupnameController = new GetGroupnameController();
        fxmlLoader.setController(getGroupnameController);
        try {
            alert.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }

        // input group members
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(config.getNewgroupselectmembersheadertext());
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GetGroupMembersController.class.getResource(config.getGetgroupmembersFxmlFilename()));
        GetGroupMembersController getGroupMembersController = new GetGroupMembersController();
        fxmlLoader.setController(getGroupMembersController);
        try {
            alert.getDialogPane().setContent(fxmlLoader.load());
            getGroupMembersController.setAvailableUsers(availableUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }
        LinkedList<SingleUser> selectedUsers = getGroupMembersController.getSelectedUsers();
        getListener().listen(new NewGroupRequest(getGroupnameController.getGroupname(), selectedUsers));
    }

    boolean isChanged (List<MessageLabel> a1, List<MessageLabel> a2) {
        if (a1.size() != a2.size())
            return true;
        for (int i = 0; i < a1.size(); i++) {
            if (!a1.get(i).getText().equals(a2.get(i).getText()) ||
                a1.get(i).getSingleChat().getChatId() != a2.get(i).getSingleChat().getChatId())
                return true;
        }
        return false;
    }
}
