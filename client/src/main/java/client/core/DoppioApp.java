package client.core;

import client.config.socketConfig.SocketConfig;
import client.core.network.RequestSender;
import client.core.network.SocketRequestSender;
import client.datatype.Page;
import client.datatype.View;
import client.dbcontroller.ChatModelController;
import client.dbcontroller.FileModelController;
import client.dbcontroller.MessageDataModelController;
import client.dbcontroller.SessionModelController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import shared.loop.Loop;
import shared.request.CheckConnection;
import shared.request.Request;
import shared.response.*;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class DoppioApp extends Application implements ResponseHandler {

    private static RequestSender sender;
    private static SessionModelController sessionModelController;
    private static MessageDataModelController messageDataModelController;
    private static ChatModelController chatModelController;
    private static FileModelController fileModelController;

    private final List<Request> requests;
    private final Loop sendRequestLoop;
    private final Loop checkConnectionLoop;
    private volatile boolean isConnected = true;

    public DoppioApp() throws InterruptedException {
        askNewSocket();
        requests = new LinkedList<>();
        sendRequestLoop = new Loop(10, this::sendRequests);
        checkConnectionLoop = new Loop(1, () -> addRequest(new CheckConnection()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        sendRequestLoop.start();
        checkConnectionLoop.start();
        ViewSwitcher.getInstance().setListener(this::addRequest);
        ViewSwitcher.getInstance().setStage(stage);

        resetUser();
        ViewSwitcher.getInstance().switchTo(new Page(View.LOGIN, -1));

        stage.setOnCloseRequest(e -> {
            LogManager.getLogger(DoppioApp.class).info("client is closed");
            System.exit(0);
        });
        stage.show();

        LogManager.getLogger(DoppioApp.class).info("stage is up");
    }

    public static void askNewSocket() {
        RequestSender newSender;
        SocketConfig socketConfig = new SocketConfig();
        try {
            newSender = new SocketRequestSender(new Socket(socketConfig.getLocalhost(), socketConfig.getPort()));
            System.out.println("successfully connected to server");
            LogManager.getLogger(DoppioApp.class).info("successfully connected to server");
            sender = newSender;
        } catch (IOException e) {
            System.out.println("connecting problem");
            LogManager.getLogger(DoppioApp.class).info("connecting problem");
        }
    }

    public static void resetUser() {
        sessionModelController = new SessionModelController();
        messageDataModelController = new MessageDataModelController();
        chatModelController = new ChatModelController();
        fileModelController = new FileModelController();
    }

    private void sendRequests() {
        List<Request> temp;

        synchronized (requests) {
            temp = new LinkedList<>();
            for (Request request : requests) {
                if (request instanceof CheckConnection || isConnected) {
                    temp.add(request);
                }
            }
            requests.removeAll(temp);
        }
        for (Request request : temp) {
            Response response = sender.send(request);
            if (response == null) {
                System.out.println("shit is null! MainResponseHandler");
            } else {
                response.handle(this);
            }
        }
    }

    private void addRequest(Request request) {
        synchronized (requests) {
            requests.add(request);
        }
    }

    @Override
    public void checkSignupResponse(SignupResponse signupResponse) {
        if (signupResponse.getErrors().size() == 0) {
            ViewSwitcher.getInstance().signupOkAction();
        } else {
            ViewSwitcher.getInstance().updateSignupPage(signupResponse);
        }
    }

    @Override
    public void checkLoginResponse(LoginResponse loginResponse) {
        if (loginResponse.getErrors().size() == 0) {
            String username = loginResponse.getSessionModel().getUsername();
            PathCreator.createClientResource(username);
            sessionModelController.setUsernameDir(username);
            messageDataModelController.setUsernameDir(username);
            chatModelController.setUsernameDir(username);
            fileModelController.setUsernameDir(username);

            sessionModelController.setNewSession(
                    loginResponse.getSessionModel()
            );

            ViewSwitcher.getInstance().loginOkAction();
        } else {
            ViewSwitcher.getInstance().updateLoginPage(loginResponse);
        }
    }

    @Override
    public void updateTimeline(GetTimelineResponse getTimelineResponse) {
        ViewSwitcher.getInstance().updateTimeline(getTimelineResponse);
    }

    @Override
    public void updateExplorer(GetExplorerResponse getExplorerResponse) {
        ViewSwitcher.getInstance().updateExplorer(getExplorerResponse);
    }

    @Override
    public void updateTweetPage(GetTweetPageResponse getTweetPageResponse) {
        ViewSwitcher.getInstance().updateTweetPage(getTweetPageResponse);
    }

    @Override
    public void updateShowTweetsPage(GetShowUserTweetsResponse getShowUserTweetsResponse) {
        ViewSwitcher.getInstance().updateShowTweetsPage(getShowUserTweetsResponse);
    }

    @Override
    public void updateShowlistPage(GetShowlistResponse getShowlistResponse) {
        ViewSwitcher.getInstance().updateShowlistPage(getShowlistResponse);
    }

    @Override
    public void updateNotificationPage(GetNotificationPageResponse getNotificationPageResponse) {
        ViewSwitcher.getInstance().updateNotificationPage(getNotificationPageResponse);
    }

    @Override
    public void updateProfile(GetProfileResponse getProfileResponse) {
        ViewSwitcher.getInstance().updateProfile(getProfileResponse);
    }

    @Override
    public void checkProfile(CheckProfile checkProfile) {
        Platform.runLater(() ->
                ViewSwitcher.getInstance().switchTo(new Page(View.PROFILEPAGE, checkProfile.getUserId())));
    }

    @Override
    public void openNewGroupAlert(OpenNewGroupAlert openNewGroupAlert) {
        ViewSwitcher.getInstance().openNewGroupAlert(openNewGroupAlert);
    }

    @Override
    public void updateMessageDataModelDB(GetMessageDataModelResponse getMessageDataModelResponse) {
        messageDataModelController.updateMessageDataModelDB(
                getMessageDataModelResponse.getMessageDataModel()
        );
    }

    @Override
    public void updateChatModelDB(GetChatModelResponse getChatModelResponse) {
        chatModelController.updateChatModelDB(
                getChatModelResponse.getChatModel()
        );
    }

    @Override
    public void updateProfilePic(GetProfilePicResponse getProfilePicResponse) {
        fileModelController.updateProfilePic(getProfilePicResponse);
    }

    @Override
    public void updateTweetPic(GetTweetPicResponse getTweetPicResponse) {
        fileModelController.updateTweetPic(getTweetPicResponse);
    }

    @Override
    public void updatePmPic(GetPmPicResponse getPmPicResponse) {
        fileModelController.updatePmPic(getPmPicResponse);
    }

    @Override
    public void socketConnectionIsDown(DisconnectResponse disconnectResponse) {
        isConnected = false;
        ViewSwitcher.getInstance().showDisconnectionMessage();
    }

    @Override
    public void socketConnectionInUp(CheckConnectionResponse checkConnectionResponse) {
        isConnected = true;
        ViewSwitcher.getInstance().showConnectionMessage();
    }

    public static SessionModelController getSessionModelController() {
        return sessionModelController;
    }

    public static MessageDataModelController getMessageDataModelController() {
        return messageDataModelController;
    }

    public static ChatModelController getChatModelController() {
        return chatModelController;
    }

    public static FileModelController getFileModelController() {
        return fileModelController;
    }
}
