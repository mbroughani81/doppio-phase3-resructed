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

public class TweetPageRootController extends BasicController {

    int tweetId;
    LinkedList<SingleTweet> curTweets;

    @FXML
    private VBox mainTweetHolder;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox tweetHolder;

    public void updatePage(SingleTweet mainTweet, LinkedList<SingleTweet> tweets) {
        if (!SingleTweetUtility.isSingleTweetListChanged(
                curTweets,
                tweets

        ))
            return;
        curTweets = tweets;

        this.clearChildControllers();
        tweetHolder.getChildren().clear();
        mainTweetHolder.getChildren().clear();
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        fxmlLoader1.setLocation(SingleTweetLabelController.class.getResource("singletweetlabel.fxml"));
        SingleTweetLabelController singleTweetLabelController1 = new SingleTweetLabelController(
                mainTweet
        );
//        singleTweetLabelController1.setListener(getListener());
        fxmlLoader1.setController(singleTweetLabelController1);
        try {
            mainTweetHolder.getChildren().add(fxmlLoader1.load());
            this.addToChildControllers(singleTweetLabelController1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }
}
