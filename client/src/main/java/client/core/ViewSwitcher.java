package client.core;

import client.apps.chat.view.ChatController;
import client.apps.mainpage.view.MainPageController;
import client.apps.messenger.view.MessengerController;
import client.apps.personalpage.view.*;
import client.apps.setting.view.SettingController;
import client.apps.tweet.view.*;
import client.datatype.BasicController;
import client.apps.authentication.view.LoginController;
import client.datatype.Page;
import client.datatype.View;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.loop.Loop;
import shared.request.RequestListener;
import shared.response.*;

import java.io.IOException;
import java.util.LinkedList;

public class ViewSwitcher {
    private Scene scene;
    private Stage stage;
    private RequestListener requestListener;
    private BasicController lastController;
    Loop updateLoop;
    Loop requestLoop;

    private static ViewSwitcher instance = null;

    public static ViewSwitcher getInstance() {
        if (instance == null)
            instance = new ViewSwitcher();
        return instance;
    }

    public void switchTo(Page page) {
        System.out.println("Switching to " + page.getView().getFileName());;
        FXMLLoader fxmlLoader = new FXMLLoader();
        switch (page.getView()) {
            case LOGIN, SIGNUP -> {
                fxmlLoader.setLocation(LoginController.class.getResource(page.getView().getFileName()));
            }
            case MAINPAGE, SETTING, PERSONALPAGE, MESSENGER, CHAT, TIMELINE, NEWTWEET, PROFILEPAGE, SHOWLIST,
                    EDITPROFILEPAGE, NOTIFICATIONPAGE, TWEETPAGE, SHOWTWEETS, EXPLORER -> {
                fxmlLoader.setLocation(MainPageController.class.getResource(View.MAINPAGE.getFileName()));
            }
        }
        switch (page.getView()) {
            case MAINPAGE -> {
                fxmlLoader.setController(new MainPageController());
            }
            case SETTING -> {
                fxmlLoader.setController(new SettingController());
            }
            case PERSONALPAGE -> {
                fxmlLoader.setController(new PersonalPageController());
            }
            case MESSENGER -> {
                fxmlLoader.setController(new MessengerController());
            }
            case CHAT -> {
                fxmlLoader.setController(new ChatController(page.getId()));
            }
            case TIMELINE -> {
                fxmlLoader.setController(new TimelineController());
            }
            case NEWTWEET -> {
                fxmlLoader.setController(new NewTweetController());
            }
            case PROFILEPAGE -> {
                fxmlLoader.setController(new ProfilePageController(page.getId()));
            }
            case SHOWLIST -> {
                fxmlLoader.setController(new ShowlistController());
            }
            case EDITPROFILEPAGE-> {
                fxmlLoader.setController(new EditProfilePageController());
            }
            case NOTIFICATIONPAGE -> {
                fxmlLoader.setController(new NotificationPageController());
            }
            case TWEETPAGE -> {
                fxmlLoader.setController(new TweetPageController(page.getId()));
            }
            case SHOWTWEETS -> {
                fxmlLoader.setController(new ShowTweetsPageController());
            }
            case EXPLORER -> {
                fxmlLoader.setController(new ExplorerController());
            }
        }
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // set listener for main controller
        BasicController basicController = fxmlLoader.getController();
        basicController.setListener(requestListener);
        lastController = basicController;
        // initializing controllers
        switch (page.getView()) {
            case SETTING -> {
                SettingController settingController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(SettingController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    settingController.setSettingRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case PERSONALPAGE -> {
                PersonalPageController personalPageController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(PersonalPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    personalPageController.setPersonalPageRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case MESSENGER -> {
                MessengerController messengerController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(MessengerController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    messengerController.setMessengerRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case CHAT -> {
                ChatController chatController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(ChatController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    chatController.setChatRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case TIMELINE -> {
                TimelineController timelineController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(TweetController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    timelineController.setTweetRootController(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case NEWTWEET -> {
                NewTweetController newTweetController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(TweetController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    newTweetController.setNewTweetRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case PROFILEPAGE -> {
                ProfilePageController profilePageController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(PersonalPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    profilePageController.setProfilePageRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case SHOWLIST -> {
                ShowlistController showlistController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(PersonalPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    showlistController.setShowlistRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case EDITPROFILEPAGE-> {
                EditProfilePageController editProfilePageController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(PersonalPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    editProfilePageController.setEditProfilePageRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case NOTIFICATIONPAGE -> {
                NotificationPageController notificationPageController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(PersonalPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    notificationPageController.setNotificationPageRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case TWEETPAGE -> {
                TweetPageController tweetPageController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(TweetPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    tweetPageController.setTweetPageRootController(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case SHOWTWEETS -> {
                ShowTweetsPageController showTweetsPageController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(TweetPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    showTweetsPageController.setTweetRootController(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case EXPLORER -> {
                ExplorerController explorerController = fxmlLoader.getController();
                FXMLLoader fxmlLoader2 = new FXMLLoader();
                fxmlLoader2.setLocation(TweetPageController.class.getResource(page.getView().getFileName()));
                try {
                    Parent root2 = fxmlLoader2.load();
                    explorerController.setExplorerRoot(root2, fxmlLoader2.getController());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        setSize(page.getView());
        Thread t = new Thread(() -> {
            changeUpdateLoop(page.getView(), basicController);
            changeRequestLoop(page.getView(), basicController);
        });
        t.start();
        getInstance().scene.setRoot(root);
        getInstance().stage.setScene(scene);
        getInstance().stage.setTitle(page.getView().getFileName());
    }

    private void setSize(View view) {
        // setting stage size
        switch (view) {
            case LOGIN, SIGNUP -> {
                getInstance().stage.setWidth(400);
                getInstance().stage.setHeight(300);
            }
            case MAINPAGE, SETTING, PERSONALPAGE, MESSENGER, CHAT, TIMELINE -> {
                getInstance().stage.setWidth(800);
                getInstance().stage.setHeight(600);
            }
        }
        getInstance().stage.setX(0);
        getInstance().stage.setY(0);
    }

    private void changeUpdateLoop(View view, BasicController basicController) {
        if (updateLoop != null) {
            updateLoop.stop();
            updateLoop = null;
        }
        updateLoop = new Loop(1, basicController.getUpdateAction());
        if (updateLoop != null) {
            updateLoop.start();
        }
    }

    private void changeRequestLoop(View view, BasicController basicController) {
        if (requestLoop != null) {
            requestLoop.stop();
            requestLoop = null;
        }
        requestLoop = new Loop(1, basicController.getRequestAction());
        if (requestLoop != null) {
            requestLoop.start();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(new Group());
        this.stage.setScene(scene);
    }

    public void setListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public void updateTimeline(GetTimelineResponse getTimelineResponse) {
        if (lastController instanceof TimelineController) {
            ((TimelineController)lastController).updateTimeline(getTimelineResponse);
        }
    }

    public void updateExplorer(GetExplorerResponse getExplorerResponse) {
        if (lastController instanceof ExplorerController) {
            ((ExplorerController)lastController).updateExplorer(getExplorerResponse);
        }
    }

    public void updateTweetPage(GetTweetPageResponse getTweetPageResponse) {
        if (lastController instanceof TweetPageController) {
            if (((TweetPageController)lastController).getTweetId() == getTweetPageResponse.getTweetId()) {
                ((TweetPageController)lastController).updatePage(getTweetPageResponse);
            }
        }
    }

    public void updateShowTweetsPage(GetShowUserTweetsResponse getShowUserTweetsResponse) {
        if (lastController instanceof ShowTweetsPageController) {
            ((ShowTweetsPageController)lastController).updateShowTweetsPage(getShowUserTweetsResponse);
        }
    }

    public void updateShowlistPage(GetShowlistResponse getShowlistResponse) {
        if (lastController instanceof ShowlistController) {
            ((ShowlistController)lastController).updateShowlistPage(getShowlistResponse);
        }
    }

    public void updateNotificationPage(GetNotificationPageResponse getNotificationPageResponse) {
        System.out.println("I shoult update notification page");
    }

    public void updateProfile(GetProfileResponse getProfileResponse) {
        if (lastController instanceof ProfilePageController) {
            ((ProfilePageController)lastController).updateProfile(getProfileResponse);
        }
    }

    public void openNewGroupAlert(OpenNewGroupAlert openNewGroupAlert) {
        if (lastController instanceof MessengerController) {
            ((MessengerController)lastController).openNewGroupAlert(openNewGroupAlert);
        }
    }

    public void signupOkAction() {
        Platform.runLater(() -> {getInstance().switchTo(new Page(View.MAINPAGE, -1));});
    }

    public void loginOkAction() {
        Platform.runLater(() -> {getInstance().switchTo(new Page(View.MAINPAGE, -1));});
    }

    public void showDisconnectionMessage(){
        if (lastController instanceof MainPageController) {
            Platform.runLater(() -> ((MainPageController)lastController).showDisconnectionMessage());
        }
    }

    public void showConnectionMessage() {
        if (lastController instanceof MainPageController) {
            Platform.runLater(() -> ((MainPageController)lastController).showConnectionMessage());
        }
    }
}
