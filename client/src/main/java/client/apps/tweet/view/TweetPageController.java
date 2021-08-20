package client.apps.tweet.view;

import client.apps.mainpage.view.MainPageController;
import javafx.application.Platform;
import javafx.scene.Parent;
import shared.request.GetTweetPageRequest;
import shared.response.GetTweetPageResponse;

public class TweetPageController extends MainPageController {

    TweetPageRootController tweetPageRootController;
    int tweetId;

    public TweetPageController(int tweetId) {
        this.tweetId = tweetId;
    }

    public void setTweetPageRootController(Parent root, TweetPageRootController tweetPageRootController) {
        setCenter(root);
        this.tweetPageRootController = tweetPageRootController;
        this.tweetPageRootController.setTweetId(tweetId);
        this.addToChildControllers(tweetPageRootController);
//        this.tweetPageRootController.setListener(getListener());
    }

    @Override
    public Runnable getRequestAction() {
        return () -> {
            getListener().listen(new GetTweetPageRequest(tweetId));
            runChildControllerRequest();
        };
    }

    public void updatePage(GetTweetPageResponse getTweetPageResponse) {
        Platform.runLater(() -> tweetPageRootController.updatePage(
                getTweetPageResponse.getMainTweet(),
                getTweetPageResponse.getTweets()
        )
        );
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }
}
