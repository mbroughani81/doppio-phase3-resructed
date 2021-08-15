package client.apps.tweet.view;

import client.apps.mainpage.view.MainPageController;
import javafx.scene.Parent;

public class NewTweetController extends MainPageController {

    NewTweetRootController newTweetRootController;
    private int mainTweetId;

    public NewTweetController(int mainTweetId) {
        this.mainTweetId = mainTweetId;
    }

    public void setNewTweetRoot(Parent root, NewTweetRootController newTweetRootController) {
        setCenter(root);
        this.newTweetRootController = newTweetRootController;
        this.newTweetRootController.setMainTweetId(mainTweetId);
        this.newTweetRootController.setListener(getListener());
    }
}
