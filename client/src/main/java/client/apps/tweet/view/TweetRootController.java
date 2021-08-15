package client.apps.tweet.view;

import client.apps.tweet.view.comp.SingleTweetLabelController;
import client.datatype.BasicController;
import shared.model.SingleTweet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.LinkedList;

public class TweetRootController extends BasicController {

//    static int cnt;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox tweetHolder;

    public void updateTweetList(LinkedList<SingleTweet> tweets) {
        tweetHolder.getChildren().clear();
        for (SingleTweet tweet : tweets) {
//            SingleTweetLabel singleTweetLabel = new SingleTweetLabel(tweet);
//            tweetHolder.getChildren().add(singleTweetLabel);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SingleTweetLabelController.class.getResource("singletweetlabel.fxml"));
            SingleTweetLabelController singleTweetLabelController = new SingleTweetLabelController(
                    tweet
            );
            singleTweetLabelController.setListener(getListener());
            fxmlLoader.setController(singleTweetLabelController);
            try {
                tweetHolder.getChildren().add(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
