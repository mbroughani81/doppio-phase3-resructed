package client.apps.authentication.view;

import client.config.apps.authentication.SignupConfig;
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
import shared.request.SignupRequest;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class SignupController extends BasicController implements Initializable {

    @FXML
    private Label panelLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField birthdayTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField bioTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    void loginClickedAction(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.LOGIN, -1));
    }

    @FXML
    void signupClickedAction(ActionEvent event) {
        getListener().listen(new SignupRequest(
                nameTextField.getText(),
                usernameTextField.getText(),
                passwordTextField.getText(),
                birthdayTextField.getText(),
                emailTextField.getText(),
                phoneTextField.getText(),
                bioTextField.getText()
        ));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SignupConfig signupConfig = new SignupConfig();
        loginButton.setText(signupConfig.getLoginButtonText());
        signupButton.setText(signupConfig.getSignupButtonText());
    }

    public void updateErrors(LinkedList<String> errors) {
        errorLabel.setText("");
        for (String error : errors) {
            errorLabel.setText(errorLabel.getText() + "\n" + error);
        }
    }

}