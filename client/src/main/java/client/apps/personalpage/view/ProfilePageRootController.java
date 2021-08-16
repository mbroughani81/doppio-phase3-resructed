package client.apps.personalpage.view;

import client.datatype.BasicController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import shared.request.GetProfileRequest;
import shared.request.NewFollowRequest;
import shared.request.NewPrivateChatRequest;
import shared.response.GetProfileResponse;

public class ProfilePageRootController extends BasicController {

    int userId;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label lastseenLabel;

    @FXML
    private Label bioLabel;

    @FXML
    private Button followButton;

    @FXML
    private Button chatButton;

    @FXML
    void chatButtonClicked(ActionEvent event) {
        getListener().listen(new NewPrivateChatRequest(userId));
    }

    @FXML
    void followButtonClicked(ActionEvent event) {
        getListener().listen(new NewFollowRequest(userId));
    }

    public void init() {
        // now, send request and wait for response;
        getListener().listen(new GetProfileRequest(userId));
//        System.out.println(userId + " is profile");
    }

    public void updateProfile(GetProfileResponse getProfileResponse) {
        usernameLabel.setText(getProfileResponse.getUsername());
        nameLabel.setText(getProfileResponse.getName());
        bioLabel.setText(getProfileResponse.getBio());
        switch (getProfileResponse.getLastSeenPrivacy()) {
            case NOBODY -> lastseenLabel.setText("nobody");
            case EVERYBODY -> lastseenLabel.setText("everybody");
            case FOLLOWING -> lastseenLabel.setText("following");
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
