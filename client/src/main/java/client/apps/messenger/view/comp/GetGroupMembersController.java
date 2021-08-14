package client.apps.messenger.view.comp;

import client.datatype.BasicController;
import shared.model.SingleUser;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class GetGroupMembersController extends BasicController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox groupMembersHolder;

    LinkedList<SingleUser> availableUsers;

    public void setAvailableUsers(LinkedList<SingleUser> availableUsers) {
        this.availableUsers = availableUsers;
        groupMembersHolder.getChildren().clear();
        for (SingleUser singleUser : availableUsers) {
            groupMembersHolder.getChildren().add(new CheckBox(String.valueOf(singleUser.getUserId())));
        }
    }

    public LinkedList<SingleUser> getSelectedUsers() {
        LinkedList<SingleUser> selectedUsers = new LinkedList<>();
        for (int i = 0; i < groupMembersHolder.getChildren().size(); i++) {
            CheckBox user = (CheckBox) groupMembersHolder.getChildren().get(i);
            if (user.isSelected())
                selectedUsers.add(availableUsers.get(i));
        }
        return selectedUsers;
    }
}
