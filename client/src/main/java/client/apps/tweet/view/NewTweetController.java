package client.apps.tweet.view;

import client.apps.mainpage.view.MainPageController;
import javafx.scene.Parent;

public class NewTweetController extends MainPageController {

    NewTweetRootController newTweetRootController;

    public void setNewTweetRoot(Parent root, NewTweetRootController newTweetRootController) {
        setCenter(root);
        this.newTweetRootController = newTweetRootController;
        this.newTweetRootController.setListener(getListener());
    }
}
