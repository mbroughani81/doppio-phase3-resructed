package client.apps.tweet.view;

import client.apps.tweet.view.comp.SingleTweetLabelController;
import client.config.apps.tweet.ExplorerRootConfig;
import client.datatype.BasicController;
import client.utils.SingleTweetUtility;
import shared.model.SingleTweet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import shared.request.ExplorerSearchRequest;

import java.io.IOException;
import java.util.LinkedList;

public class ExplorerRootController extends BasicController {

    private LinkedList<SingleTweet> curTweets;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox tweetHolder;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    void searchButtonClicked(ActionEvent event) {
        getListener().listen(new ExplorerSearchRequest(searchTextField.getText()));
    }

    public void updateExplorer(LinkedList<SingleTweet> tweets) {

        // fixme : for loading images, one should open that image atleast on time before
        if (!SingleTweetUtility.isSingleTweetListChanged(
                curTweets,
                tweets

        ))
            return;
        curTweets = tweets;

        ExplorerRootConfig explorerRootConfig = new ExplorerRootConfig();
        tweetHolder.getChildren().clear();
        this.clearChildControllers();
        for (SingleTweet tweet : tweets) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(SingleTweetLabelController.class.getResource(explorerRootConfig.getSingleTweetLabelFxmlFilename()));
            SingleTweetLabelController singleTweetLabelController = new SingleTweetLabelController(
                    tweet
            );
            singleTweetLabelController.setListener(getListener());
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
