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
    private Button multipmButton;

    @FXML
    private Button newgroupButton;

    @FXML
    private Button newtypeButton;

    @FXML
    void multipmButtonClicked(ActionEvent event) {
        getListener().listen(new GetMultipmAlertData());
    }

    @FXML
    void newgroupButtonClicked(ActionEvent event) {
        getListener().listen(new GetNewGroupAlertData());
    }

    @FXML
    void newtypeButtonClicked(ActionEvent event) {
        getListener().listen(new GetNewTypeAlertData());
    }


    @Override
    public void changed(ObservableValue<? extends MessageLabel> observable, MessageLabel oldValue, MessageLabel newValue) {
        int id = listView.getSelectionModel().getSelectedItem().getChatId();
        ViewSwitcher.getInstance().switchTo(new Page(View.CHAT, id));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.getSelectionModel().selectedItemProperty().addListener(this);

        MessengerRootConfig messengerRootConfig = new MessengerRootConfig();
        multipmButton.setText(messengerRootConfig.getMultipmButtonText());
        newgroupButton.setText(messengerRootConfig.getNewgroupButtonText());
        newtypeButton.setText(messengerRootConfig.getNewtypeButtonText());
    }

    @Override
    public Runnable getUpdateAction() {
        return () -> {
            MessageDataModel messageDataModel = DoppioApp.getMessageDataModelController().getMessageDataModel();
            LinkedList<MessageLabel> newItems = new LinkedList<>();
            for (Pair<Integer, String> pair : messageDataModel.getChatModelIds()) {
                newItems.add(new MessageLabel(pair.getFirst() + " " + pair.getSecond() + "\n", pair.getFirst()));
            }
            if (!isChanged(listView.getItems(), newItems)) {
                return;
            }
            listView.getItems().clear();
            for (Pair<Integer, String> pair : messageDataModel.getChatModelIds()) {
                listView.getItems().add(new MessageLabel(pair.getFirst() + " " + pair.getSecond() + "\n", pair.getFirst()));
            }
        };
    }

    public void openNewGroupAlert(LinkedList<SingleUser> availableUsers) {
//        System.out.println(" all users are : " );
//        for (SingleUser user : availableUsers)
//            System.out.println(user.getUserId());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Input groupname");
        alert.setHeaderText("Enter groupname : ");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GetGroupnameController.class.getResource("getgroupname.fxml"));
        GetGroupnameController getGroupnameController = new GetGroupnameController();
        fxmlLoader.setController(getGroupnameController);
        try {
            alert.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("finished 1 !");
        if (result.get() == ButtonType.CANCEL) {
            return;
        }
        System.out.println(getGroupnameController.getGroupname() + " is result");

        // input group members
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selecting members");
        alert.setHeaderText("Select members to add group : ");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GetGroupMembersController.class.getResource("getgroupmembers.fxml"));
        GetGroupMembersController getGroupMembersController = new GetGroupMembersController();
        fxmlLoader.setController(getGroupMembersController);
        try {
            alert.getDialogPane().setContent(fxmlLoader.load());
            getGroupMembersController.setAvailableUsers(availableUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = alert.showAndWait();
        System.out.println("finished 2!");
        if (result.get() == ButtonType.CANCEL) {
            return;
        }
        System.out.println(getGroupnameController.getGroupname() + " is result");
        LinkedList<SingleUser> selectedUsers = getGroupMembersController.getSelectedUsers();
        getListener().listen(new NewGroupRequest(getGroupnameController.getGroupname(), selectedUsers));
    }

    boolean isChanged (List<MessageLabel> a1, List<MessageLabel> a2) {
        if (a1.size() != a2.size())
            return true;
        for (int i = 0; i < a1.size(); i++) {
            if (!a1.get(i).getText().equals(a2.get(i).getText()) ||
                a1.get(i).getChatId() != a2.get(i).getChatId())
                return true;
        }
        return false;
    }
}
