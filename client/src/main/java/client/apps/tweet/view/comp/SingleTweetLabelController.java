package client.apps.tweet.view.comp;

import client.core.DoppioApp;
import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import client.dbcontroller.FileModelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import shared.model.SingleTweet;
import shared.request.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleTweetLabelController extends BasicController implements Initializable {

    private SingleTweet singleTweet;

    @FXML
    private Label profileLabel;

    @FXML
    private Label tweetTextLabel;

    @FXML
    private Label tweetimagelabel;

    @FXML
    private Button likeButton;

    @FXML
    private Button retweetButton;

    @FXML
    private Button commentButton;

    @FXML
    void commentButtonClicked(ActionEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.NEWTWEET, singleTweet.getTweetId()));
    }

    @FXML
    void likeButtonClicked(ActionEvent event) {
        getListener().listen(new NewLikeTweetRequest(singleTweet.getTweetId()));
    }

    @FXML
    void profileLabelClicked(MouseEvent event) {
        ViewSwitcher.getInstance().switchTo(new Page(View.PROFILEPAGE, singleTweet.getUserId()));
    }

    @FXML
    void retweetButtonClicked(ActionEvent event) {
        getListener().listen(new NewRetweetRequest(singleTweet.getTweetId()));
    }


    @FXML
    void tweetTextLabelClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ViewSwitcher.getInstance().switchTo(new Page(View.TWEETPAGE, singleTweet.getTweetId()));
        }
    }

    public SingleTweetLabelController(SingleTweet singleTweet) {
        this.singleTweet = singleTweet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweetTextLabel.setText(singleTweet.getText());
        profileLabel.setText("");
        // load the tweet pics
        if (DoppioApp.getFileModelController().profileExists(singleTweet.getUserId())) {
            ImageView view;
            File img = new File(DoppioApp.getFileModelController().getProfilePicPath(
                    singleTweet.getUserId()
            ));
            InputStream isImage = null;
            try {
                isImage = new FileInputStream(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            view = new ImageView(new Image(isImage));
            view.setFitWidth(40);
            view.setFitHeight(40);
            profileLabel.setGraphic(view);
        } else {
            ImageView view;
            view = new ImageView(new Image("iliya1.png"));
            view.setFitWidth(40);
            view.setFitHeight(40);
            profileLabel.setGraphic(view);
        }
        // load the tweet image
        if (DoppioApp.getFileModelController().tweetExists(singleTweet.getTweetId())) {
            ImageView view;
            File img = new File(DoppioApp.getFileModelController().getTweetPicPath(
                    singleTweet.getTweetId()
            ));
            InputStream isImage = null;
            try {
                isImage = new FileInputStream(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            view = new ImageView(new Image(isImage));
            view.setPreserveRatio(true);
            view.setFitWidth(400);
            tweetimagelabel.setGraphic(view);
        } else {
//            ImageView view;
//            view = new ImageView(new Image("iliya1.png"));
//            view.setFitWidth(100);
//            view.setFitHeight(400);
//            profileLabel.setGraphic(view);
        }

        // setting context meni
        ContextMenu contextMenu = new ContextMenu();
        MenuItem saveToSavedMessagesItem = new MenuItem("save to saved messages");
        saveToSavedMessagesItem.setOnAction((event) -> {
            System.out.println("save to saved clickec" + singleTweet.getText());
            getListener().listen(new SaveTweetInSavedMessageRequest(singleTweet.getTweetId()));
        });
        MenuItem forwardItem = new MenuItem("forward");
        forwardItem.setOnAction((event) -> {
            System.out.println("forward clicked" + singleTweet.getText());
//            openDeleteDialog();
        });


        MenuItem blockItem = new MenuItem("block");
        blockItem.setOnAction((event) -> {
            System.out.println("block clicked" + singleTweet.getText());
            getListener().listen(new NewBlockRequest(singleTweet.getUserId()));
        });
        MenuItem muteItem = new MenuItem("mute");
        muteItem.setOnAction((event) -> {
            System.out.println("mute clicked" + singleTweet.getText());
            getListener().listen(new NewMuteRequest(singleTweet.getUserId()));
        });

        MenuItem reportSpamItem = new MenuItem("report spam");
        reportSpamItem.setOnAction((event) -> {
            System.out.println("report clicked" + singleTweet.getText());
            getListener().listen(new NewReportSpamRequest(singleTweet.getTweetId()));
        });

        contextMenu.getItems().addAll(
                saveToSavedMessagesItem,
                forwardItem,
                blockItem,
                muteItem,
                reportSpamItem
        );
        tweetTextLabel.setContextMenu(contextMenu);
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            if (FileModelController.canUpdate("profilepics/" + singleTweet.getUserId() + ".jpg")) {
                getListener().listen(new GetProfilePicRequest(singleTweet.getUserId()));
            }
            if (FileModelController.canUpdate("tweetpics/" + singleTweet.getTweetId() + ".jpg")) {
                getListener().listen(new GetTweetPicRequest(singleTweet.getTweetId()));
            }
        };
    }
}
