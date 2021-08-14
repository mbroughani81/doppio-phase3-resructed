package client.apps.personalpage.view;

import client.apps.tweet.view.TweetController;
import javafx.application.Platform;
import shared.request.GetShowUserTweetsRequest;
import shared.response.GetShowUserTweetsResponse;

public class ShowTweetsPageController extends TweetController {

    public void updateShowTweetsPage(GetShowUserTweetsResponse getShowUserTweetsResponse) {
        Platform.runLater(() -> tweetRootController.updateTweetList(getShowUserTweetsResponse.getSingleTweets()));
    }

    @Override
    public Runnable getUpdateAction() {
        return super.getUpdateAction();
    }

    @Override
    public Runnable getRequestAction() {
        return () -> getListener().listen(new GetShowUserTweetsRequest());
    }
}
