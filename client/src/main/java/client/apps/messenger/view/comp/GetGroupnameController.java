package client.apps.messenger.view.comp;

import client.datatype.BasicController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GetGroupnameController extends BasicController {

    @FXML
    private TextField groupnameTextField;

    public String getGroupname() {
        return groupnameTextField.getText();
    }
}
