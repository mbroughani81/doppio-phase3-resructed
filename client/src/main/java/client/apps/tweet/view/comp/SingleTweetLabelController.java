package client.apps.tweet.view.comp;

import client.core.ViewSwitcher;
import client.datatype.BasicController;
import client.datatype.Page;
import client.datatype.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import shared.model.SingleTweet;
import shared.request.NewLikeTweetRequest;
import shared.request.NewRetweetRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleTweetLabelController extends BasicController implements Initializable {

    private SingleTweet singleTweet;

    @FXML
    private Label profileLabel;

    @FXML
    private Label tweetTextLabel;

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
        ViewSwitcher.getInstance().switchTo(new Page(View.TWEETPAGE, singleTweet.getTweetId()));
    }

    public SingleTweetLabelController(SingleTweet singleTweet) {
        this.singleTweet = singleTweet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweetTextLabel.setText(singleTweet.getText());
        profileLabel.setText("");
        ImageView view = new ImageView(new Image("squScreenshot (91).png"));
        view.setFitWidth(40);
        view.setFitHeight(40);
        profileLabel.setGraphic(view);
    }
}
