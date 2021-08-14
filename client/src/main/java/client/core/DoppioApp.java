package client.core;

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
    //    private RequestSender sender;
    private final List<Request> requests;
    private final Loop loop;
    private final Loop checkConnection;
    private volatile boolean isConnected = true;
    private static String username;

    private static SessionModelController sessionModelController = new SessionModelController();
    private static MessageDataModelController messageDataModelController = new MessageDataModelController();
    private static ChatModelController chatModelController = new ChatModelController();
    private static FileModelController fileModelController = new FileModelController();

    public DoppioApp() throws InterruptedException {
//        sender = new SocketRequestSender(new Socket("localhost", 8001));
        askNewSocket();
        requests = new LinkedList<>();
        loop = new Loop(10, this::sendRequests);
        checkConnection = new Loop(1, () -> {
            addRequest(new CheckConnection());
        });
    }

    public static void askNewSocket() {
//        while (true) {
//            try {
//                sender = new SocketRequestSender(new Socket("localhost", 8001));
//                System.out.println("successfully connected to server");
//                break;
//            } catch (IOException e) {
//                System.out.println("problem connecting. please ea");
//                Thread.sleep(5000);
//            }
//        }
        RequestSender newSender;
        try {
            newSender = new SocketRequestSender(new Socket("localhost", 8001));
            System.out.println("successfully connected to server");
            sender = newSender;
        } catch (IOException e) {
            System.out.println("problem connecting. please ea");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        loop.start();
        checkConnection.start();
        ViewSwitcher.getInstance().setListener(this::addRequest);
        ViewSwitcher.getInstance().setStage(stage);
        ViewSwitcher.getInstance().switchTo(new Page(View.LOGIN, -1));
        stage.show();
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

    public static String getUsername() {
        return username;
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

    @Override
    public void checkSignupResponse(SignupResponse signupResponse) {
//        System.out.println("DoppioApp number of errors is : " + signupResponse.getErrors().size());
        ViewSwitcher.getInstance().signupOkAction();
    }

    @Override
    public void checkLoginResponse(LoginResponse loginResponse) {
        if (loginResponse.isOk()) {
            username = loginResponse.getUsername();
            PathCreator.createClientResource(username);
            sessionModelController.setUsernameDir(username);
            messageDataModelController.setUsernameDir(username);
            chatModelController.setUsernameDir(username);
            fileModelController.setUsernameDir(username);

            sessionModelController.setNewSession(loginResponse.getUsername(), loginResponse.getAuthToken());

            ViewSwitcher.getInstance().loginOkAction();
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
        System.out.println("new has come : " + (getProfilePicResponse.getImageString() == null));
        fileModelController.updateProfilePic(getProfilePicResponse);
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
}
