package client.apps.personalpage.view;

import client.datatype.BasicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import shared.request.*;
import shared.util.ImageSerializer;

import java.io.File;
import java.util.Optional;

public class EditProfilePageRootController extends BasicController {

    @FXML
    private Button newprofileButton;

    @FXML
    private Button changenameButton;

    @FXML
    private Button changebirthdayButton;

    @FXML
    private Button changeemailButton;

    @FXML
    private Button changephonenumberButton;

    @FXML
    private Button changebioButton;

    @FXML
    void changebioButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New bio");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new bio :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangeBioRequest(result.get())));
    }

    @FXML
    void changebirthdayClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New birthday");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new birthday :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangeBirthdayRequest(result.get())));
    }

    @FXML
    void changeemailButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New email");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new email :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangeEmailRequest(result.get())));
    }

    @FXML
    void changenameButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New name");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new name :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangeNameRequest(result.get())));
    }

    @FXML
    void changephonenumberButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New phonenumber");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new phonenumber :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangePhonenumberRequest(result.get())));
    }

    @FXML
    void newprofileButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose new profile");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("image", "*.jpg", "*.jpeg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(newprofileButton.getScene().getWindow());
        getListener().listen(new ChangeProfileRequest(ImageSerializer.encodeFileToBase64Binary(selectedFile)));
    }
}
