package client.apps.tweet.view;

import client.apps.tweet.view.comp.SingleTweetLabelController;
import client.datatype.BasicController;
import client.utils.SingleTweetUtility;
import shared.model.SingleTweet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.LinkedList;

public class TweetRootController extends BasicController {

    private LinkedList<SingleTweet> curTweets;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox tweetHolder;

    public void updateTweetList(LinkedList<SingleTweet> tweets) {
        // if nothing changed, move on
        if (!SingleTweetUtility.isSingleTweetListChanged(curTweets, tweets))
            return;
        curTweets = tweets;

        this.clearChildControllers();
        tweetHolder.getChildren().clear();
        for (SingleTweet tweet : tweets) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SingleTweetLabelController.class.getResource("singletweetlabel.fxml"));
            SingleTweetLabelController singleTweetLabelController = new SingleTweetLabelController(
                    tweet
            );
//            singleTweetLabelController.setListener(getListener());
            fxmlLoader.setController(singleTweetLabelController);
            try {
                tweetHolder.getChildren().add(fxmlLoader.load());
                this.addToChildControllers(singleTweetLabelController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            runChildControllerRequest();
        };
    }
}
