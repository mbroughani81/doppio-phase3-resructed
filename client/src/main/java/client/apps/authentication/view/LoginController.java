package client.apps.authentication.view;

import client.config.apps.authentication.LoginConfig;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shared.request.LoginRequest;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class LoginController extends BasicController implements Initializable {

    @FXML
    private Label panelLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    void loginButtonClicked(ActionEvent event) {
        getListener().listen(new LoginRequest(
                usernameTextField.getText(),
                passwordTextField.getText()
        ));
    }

    @FXML
    void signupButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.SIGNUP, -1));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginConfig loginConfig = new LoginConfig();
        loginButton.setText(loginConfig.getLoginButtonText());
        signupButton.setText(loginConfig.getSignupButtonText());
    }

    public void updateErrors(LinkedList<String> errors) {
        errorLabel.setText("");
        for (String error : errors) {
            errorLabel.setText(errorLabel.getText() + "\n" + error);
        }
    }
}
