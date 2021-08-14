package client.apps.setting.view;

import client.datatype.BasicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import shared.datatype.LastSeenPrivacy;
import shared.datatype.Privacy;
import shared.request.*;

import java.util.LinkedList;
import java.util.Optional;

public class SettingRootController extends BasicController {

    @FXML
    private Button privacyButton;

    @FXML
    private Button lastseenprivacyButton;

    @FXML
    private Button setactivityButton;

    @FXML
    private Button changepasswordButton;

    @FXML
    private Button deleteaccountButton;

    @FXML
    private Button logoutButton;

    @FXML
    void changepasswordButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New password");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new password :");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangePasswordRequest(result.get())));
    }

    @FXML
    void deleteaccountButtonClicked(ActionEvent event) {

    }

    @FXML
    void lastseenprivacyButtonClicked(ActionEvent event) {
        LinkedList<String> choices = new LinkedList<>();
        choices.add("Nobody");
        choices.add("Everybody");
        choices.add("Following");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Following", choices);
        dialog.setTitle("Change lastseenprivacy");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose lastseenprivacy : ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            switch (letter) {
                case "Nobody" -> {
                    getListener().listen(new ChangeLastseenprivacyRequest(LastSeenPrivacy.NOBODY));
                }
                case "Everybody" -> {
                    getListener().listen(new ChangeLastseenprivacyRequest(LastSeenPrivacy.EVERYBODY));
                }
                case "Following" -> {
                    getListener().listen(new ChangeLastseenprivacyRequest(LastSeenPrivacy.FOLLOWING));
                }
            }
        });
    }

    @FXML
    void logoutButtonClicked(ActionEvent event) {

    }

    @FXML
    void privacyButtonClicked(ActionEvent event) {
        LinkedList<String> choices = new LinkedList<>();
        choices.add("Private");
        choices.add("Public");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Private", choices);
        dialog.setTitle("Change privacy");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose privacy : ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            switch (letter) {
                case "Private" -> {
                    getListener().listen(new ChangePrivacyRequest(Privacy.PRIVATE));
                }
                case "Public" -> {
                    getListener().listen(new ChangePrivacyRequest(Privacy.PUBLIC));
                }
            }
        });
    }

    @FXML
    void setactivityButtonClicked(ActionEvent event) {
        LinkedList<String> choices = new LinkedList<>();
        choices.add("Active");
        choices.add("Deactive");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Active", choices);
        dialog.setTitle("Change activity");
        dialog.setHeaderText(null);
        dialog.setContentText("Choose activity : ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            switch (letter) {
                case "Active" -> {
                    getListener().listen(new ChangeActivityRequest(true));
                }
                case "Deactive" -> {
                    getListener().listen(new ChangeActivityRequest(false));
                }
            }
        });
    }
}
