package client.apps.personalpage.view;

import client.core.DoppioApp;
import client.datatype.BasicController;
import client.dbcontroller.FileModelController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import shared.request.*;
import shared.response.GetProfileResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageRootController extends BasicController implements Initializable {

    private int userId;

    @FXML
    private Label profilepicLabel;

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
    private Button blockButton;

    @FXML
    void blockButtonClicked(ActionEvent event) {
        getListener().listen(new NewBlockRequest(userId));
    }

    @FXML
    void chatButtonClicked(ActionEvent event) {
        getListener().listen(new NewPrivateChatRequest(userId));
    }

    @FXML
    void followButtonClicked(ActionEvent event) {
        getListener().listen(new NewFollowRequest(userId));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            if (FileModelController.canUpdate("profilepics/" + userId + ".jpg")) {
                getListener().listen(new GetProfilePicRequest(userId));
            }
        };
    }

    public void init() {
        getListener().listen(new GetProfileRequest(userId));
        if (DoppioApp.getFileModelController().profileExists(userId)) {
            ImageView view;
            File img = new File(DoppioApp.getFileModelController().getProfilePicPath(
                    userId
            ));
            InputStream isImage = null;
            try {
                isImage = new FileInputStream(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            view = new ImageView(new Image(isImage));
            view.setFitWidth(150);
            view.setFitHeight(150);
            profilepicLabel.setGraphic(view);
        } else {
            Rectangle rectangle = new Rectangle(150, 150);
            rectangle.setFill(Paint.valueOf("#fa7e5c"));
            profilepicLabel.setGraphic(rectangle);
        }
    }

    public void updateProfile(GetProfileResponse getProfileResponse) {
        usernameLabel.setText(getProfileResponse.getUsername());
        nameLabel.setText(getProfileResponse.getName());
        bioLabel.setText(getProfileResponse.getBio());
        lastseenLabel.setText(getProfileResponse.getLastseen());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        System.out.println(userId + "is id!");
        this.userId = userId;
    }
}
