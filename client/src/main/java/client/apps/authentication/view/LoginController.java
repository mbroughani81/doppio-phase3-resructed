package client.apps.authentication.view;

import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shared.request.LoginRequest;

import java.util.LinkedList;

public class LoginController extends BasicController {

    @FXML
    private Label panelLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button signupButton;

    @FXML
    void loginClickAction(ActionEvent event) {
        getListener().listen(new LoginRequest(
                usernameTextField.getText(),
                passwordTextField.getText()
        ));
    }

    @FXML
    void signupClickAction(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.SIGNUP, -1));
    }

    public void updateErrors(LinkedList<String> errors) {
        errorLabel.setText("");
        for (String error : errors) {
            errorLabel.setText(errorLabel.getText() + "\n" + error);
        }
    }
}
