package client.apps.tweet.view;

import client.apps.mainpage.view.MainPageController;
import javafx.scene.Parent;

public class TweetController extends MainPageController {

    protected TweetRootController tweetRootController;

    public void setTweetRootController(Parent root, TweetRootController tweetRootController) {
        setCenter(root);
        this.tweetRootController = tweetRootController;
        this.tweetRootController.setListener(getListener());
    }

    @Override
    public Runnable getUpdateAction() {
        return super.getUpdateAction();
    }

    @Override
    public Runnable getRequestAction() {
        return super.getRequestAction();
    }
}
