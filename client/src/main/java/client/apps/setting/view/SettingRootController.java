package client.apps.setting.view;

import client.config.apps.setting.SettingRootConfig;
import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import shared.datatype.LastSeenPrivacy;
import shared.datatype.Privacy;
import shared.request.*;

import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingRootController extends BasicController implements Initializable {

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
        SettingRootConfig config = new SettingRootConfig();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(config.getChangepassworddialogContentText());
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> getListener().listen(new ChangePasswordRequest(result.get())));
    }

    @FXML
    void deleteaccountButtonClicked(ActionEvent event) {
        getListener().listen(new DeleteAccountRequest(DoppioApp.getSessionModelController().getSession().getUserId()));
        DoppioApp.resetUser();
        ViewSwitcher.getInstance().switchTo(new Page(View.LOGIN, -1));
    }

    @FXML
    void lastseenprivacyButtonClicked(ActionEvent event) {
        SettingRootConfig config = new SettingRootConfig();
        LinkedList<String> choices = new LinkedList<>();
        choices.add(config.getLastseenchoicesNobodyText());
        choices.add(config.getLastseenchoicesEverybodyText());
        choices.add(config.getLastseenchoicesFollowingText());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(config.getLastseenchoicesFollowingText(), choices);
        dialog.setHeaderText(null);
        dialog.setContentText(config.getChangelastseendialogContentText());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            if (letter.equals(config.getLastseenchoicesNobodyText())) {
                getListener().listen(new ChangeLastseenprivacyRequest(LastSeenPrivacy.NOBODY));
            }
            if (letter.equals(config.getLastseenchoicesEverybodyText())) {
                getListener().listen(new ChangeLastseenprivacyRequest(LastSeenPrivacy.EVERYBODY));
            }
            if (letter.equals(config.getLastseenchoicesFollowingText())) {
                getListener().listen(new ChangeLastseenprivacyRequest(LastSeenPrivacy.FOLLOWING));
            }
        });
    }

    @FXML
    void logoutButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.LOGIN, -1));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DoppioApp.resetUser();
    }

    @FXML
    void privacyButtonClicked(ActionEvent event) {
        SettingRootConfig config = new SettingRootConfig();
        LinkedList<String> choices = new LinkedList<>();
        choices.add(config.getPrivacychoicesPrivateText());
        choices.add(config.getPrivacychoicesPublicText());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(config.getPrivacychoicesPrivateText(), choices);
        dialog.setHeaderText(null);
        dialog.setContentText(config.getChangeprivacydialogContentText());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            if (letter.equals(config.getPrivacychoicesPrivateText())) {
                getListener().listen(new ChangePrivacyRequest(Privacy.PRIVATE));
            }
            if (letter.equals(config.getPrivacychoicesPublicText())) {
                getListener().listen(new ChangePrivacyRequest(Privacy.PUBLIC));
            }
        });
    }

    @FXML
    void setactivityButtonClicked(ActionEvent event) {
        SettingRootConfig config = new SettingRootConfig();
        LinkedList<String> choices = new LinkedList<>();
        choices.add(config.getActivityActiveText());
        choices.add(config.getActivityDeactiveText());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(config.getActivityActiveText(), choices);
        dialog.setHeaderText(null);
        dialog.setContentText(config.getChangeactivitydialogContentText());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            if (letter.equals(config.getActivityActiveText())) {
                getListener().listen(new ChangeActivityRequest(true));
            }
            if (letter.equals(config.getActivityDeactiveText())) {
                getListener().listen(new ChangeActivityRequest(false));
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SettingRootConfig settingRootConfig = new SettingRootConfig();
        privacyButton.setText(settingRootConfig.getPrivacyButtonText());
        lastseenprivacyButton.setText(settingRootConfig.getLastseenprivacyButtonText());
        setactivityButton.setText(settingRootConfig.getSetactivityButtonText());
        changepasswordButton.setText(settingRootConfig.getChangepasswordButtonText());
        deleteaccountButton.setText(settingRootConfig.getDeleteaccountButtonText());
        logoutButton.setText(settingRootConfig.getLogoutButtonText());
    }
}
