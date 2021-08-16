package server.controller;

import org.apache.logging.log4j.LogManager;
import server.dbcontroller.*;
import shared.datatype.Pair;
import server.controller.network.SocketResponseSender;
import server.model.*;
import shared.model.*;
import shared.request.*;
import shared.response.*;

import java.util.LinkedList;

public class ClientThread extends Thread implements RequestHandler {

    SocketResponseSender sender;
    private volatile boolean running;

    AuthController authController = new AuthController();
    MessageController messageController = new MessageController();
    TweetController tweetController = new TweetController();
    SocialController socialController = new SocialController();
    SessionController sessionController = new SessionController();
    FileController fileController = new FileController();

    public ClientThread(SocketResponseSender sender) {
        this.sender = sender;
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        LogManager.getLogger(ClientThread.class).debug("ClientThread run is started");
        while (running) {
            Request request = sender.getRequest();
            sender.sendResponse(request.handle(this));
        }
    }

    @Override
    public Response signupUser(SignupRequest signupRequest) {
        LogManager.getLogger(ClientThread.class).info("SignupRequest is getting handled");

        LinkedList<String> errors = new LinkedList<>();
        if (authController.getUser(signupRequest.getUsername()) != null)
            errors.add("Username already used");
        if (signupRequest.getPassword().length() == 0)
            errors.add("Password can not be empty");
        if (errors.size() == 0)
            authController.signupUser(signupRequest);

        return new SignupResponse(errors);
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        LogManager.getLogger(ClientThread.class).info("LoginRequest is getting handled");

        LinkedList<String> errors = new LinkedList<>();
        if (authController.getUser(loginRequest.getUsername()) == null) {
            errors.add("User not found");
        } else {
            if (!authController.getUser(loginRequest.getUsername()).getPassword().equals(loginRequest.getPassword())) {
                errors.add("Password is not correct");
            } else {
                sessionController.setUserSession(loginRequest.getUsername());
                return new LoginResponse(
                        loginRequest.getUsername(),
                        sessionController.getUserSession(loginRequest.getUsername()).getAuthToken(),
                        errors
                );
            }
        }
        return new LoginResponse(
                loginRequest.getUsername(),
                null,
                errors
        );
    }
//
//        return new LoginResponse(
//                loginRequest.getUsername(),
//                user != null && user.getPassword().equals(loginRequest.getPassword()),
//                sessionController.getUserSession(loginRequest.getUsername()).getAuthToken(),
//                new LinkedList<>()
//        );
    @Override
    public Response newPrivateChat(NewPrivateChatRequest newPrivateChatRequest) {
        LogManager.getLogger(ClientThread.class).info("NewPrivateChatRequest is getting handled");

        if (newPrivateChatRequest.getUser1Id() == -1) {
            newPrivateChatRequest.setUser1Id(
                    authController.getUserWithAuthToken(newPrivateChatRequest.getAuthToken()).getId()
            );
        }

        messageController.newPrivateChat(newPrivateChatRequest);
        return new NullResponse("salam");
    }

    @Override
    public Response newTweet(NewTweetRequest newTweetRequest) {
        LogManager.getLogger(ClientThread.class).info("NewTweetRequest is getting handled");


        if (newTweetRequest.getCreatorId() == -1) {
            newTweetRequest.setCreatorId(authController.getUserWithAuthToken(newTweetRequest.getAuthToken()).getId());
        }
        int tweetId = tweetController.newTweet(newTweetRequest);
        if (newTweetRequest.getImageString() != null)
            fileController.updateTweet(tweetId, newTweetRequest.getImageString());
        return new NullResponse("salam");
    }

    @Override
    public Response newPm(NewPmRequest newPmRequest) {
        LogManager.getLogger(ClientThread.class).info("NewPmRequest is getting handled");


        if (newPmRequest.getUserId() == -1) {
            newPmRequest.setUserId(authController.getUserWithAuthToken(newPmRequest.getAuthToken()).getId());
        }
        messageController.sendNewPm(newPmRequest);
        return new NullResponse("salam");
    }

    @Override
    public Response newFollow(NewFollowRequest newFollowRequest) {
        LogManager.getLogger(ClientThread.class).info("NewFollowRequest is getting handled");

        if (newFollowRequest.getFollowerId() == -1) {
            newFollowRequest.setFollowerId(authController.getUserWithAuthToken(newFollowRequest.getAuthToken()).getId());
        }
        socialController.newFollow(newFollowRequest);
        return new NullResponse("newFollow is ok");
    }

    @Override
    public Response newGroup(NewGroupRequest newGroupRequest) {
        LogManager.getLogger(ClientThread.class).info("NewGroupRequest is getting handled");

        if (newGroupRequest.getOwnerId() == -1) {
            newGroupRequest.setOwnerId(authController.getUserWithAuthToken(newGroupRequest.getAuthToken()).getId());
        }
        messageController.newGroupChat(newGroupRequest);
        return new NullResponse("group is created");
    }

    @Override
    public Response newRetweet(NewRetweetRequest newRetweetRequest) {
        tweetController.newRetweet(newRetweetRequest);
        return new CheckConnectionResponse();
    }

    @Override
    public Response newLikeTweet(NewLikeTweetRequest newLikeTweetRequest) {
        tweetController.newLike(newLikeTweetRequest);
        return new CheckConnectionResponse();
    }

    @Override
    public Response changeBio(ChangeBioRequest changeBioRequest) {
        LogManager.getLogger(ClientThread.class).info("ChangeBioRequest is getting handled");

        authController.changeBio(changeBioRequest);
        return new NullResponse("bio changed");
    }

    @Override
    public Response changeName(ChangeNameRequest changeNameRequest) {
        LogManager.getLogger(ClientThread.class).info("ChangeNameRequest is getting handled");

        authController.changeName(changeNameRequest);
        return new NullResponse("name changed");
    }

    @Override
    public Response changeEmail(ChangeEmailRequest changeEmailRequest) {
        LogManager.getLogger(ClientThread.class).info("ChangeEmailRequest is getting handled");

        authController.changeEmail(changeEmailRequest);
        return new NullResponse("email changed");
    }

    @Override
    public Response changePhonenumber(ChangePhonenumberRequest changePhonenumberRequest) {
        LogManager.getLogger(ClientThread.class).info("ChangePhonenumberRequest is getting handled");

        authController.changePhonenumber(changePhonenumberRequest);
        return new NullResponse("phonenumber changed");
    }

    @Override
    public Response changeBirthday(ChangeBirthdayRequest changeBirthdayRequest) {
        LogManager.getLogger(ClientThread.class).info("ChangeBirthdayRequest is getting handled");

        authController.changeBirthday(changeBirthdayRequest);
        return new NullResponse("birthday changed");
    }

    @Override
    public Response changeProfile(ChangeProfileRequest changeProfileRequest) {
        LogManager.getLogger(ClientThread.class).info("ChangeProfileRequest is getting handled");

        User user = authController.getUserWithAuthToken(changeProfileRequest.getAuthToken());
        FileController fileController = new FileController();
        fileController.updateProfile(user.getId(), changeProfileRequest.getImageString());
        return new NullResponse("profile changed");
    }

    @Override
    public Response getTimeline(GetTimelineRequest getTimelineRequest) {
        LogManager.getLogger(ClientThread.class).info("GetTimelineRequest is getting handled");

        LinkedList<Tweet> tweets = tweetController.getTimeline(
                authController.getUserWithAuthToken(getTimelineRequest.getAuthToken()).getId()
        );
        LinkedList<SingleTweet> resTweets = TweetController.convertToSingleTweet(tweets);
        return new GetTimelineResponse(resTweets);
    }

    @Override
    public Response getExplorer(GetExplorerRequest getExplorerRequest) {
        LogManager.getLogger(ClientThread.class).info("GetExplorerRequest is getting handled");

//        LinkedList<Tweet> tweets = tweetController.getTimeline(
//                authController.getUserWithAuthToken(getExplorerRequest.getAuthToken()).getUsername()
//        );
        LinkedList<Tweet> tweets = tweetController.getExplorer(
                authController.getUserWithAuthToken(getExplorerRequest.getAuthToken()).getId()
        );
        LinkedList<SingleTweet> resTweets = TweetController.convertToSingleTweet(tweets);
        return new GetExplorerResponse(resTweets);
    }

    @Override
    public Response getTweetPage(GetTweetPageRequest getTweetPageRequest) {
        LogManager.getLogger(ClientThread.class).info("GetTweetPageRequest is getting handled");

        LinkedList<Tweet> tweets = tweetController.getTweetComments(
            getTweetPageRequest.getTweetId()
        );
        LinkedList<SingleTweet> resTweets = TweetController.convertToSingleTweet(tweets);

        Tweet tweet = tweetController.getTweet(getTweetPageRequest.getTweetId());
        SingleTweet mainTweet = TweetController.convertToSingleTweet(tweet);

        return new GetTweetPageResponse(getTweetPageRequest.getTweetId(), mainTweet, resTweets);
    }

    @Override
    public Response getShowUserTweets(GetShowUserTweetsRequest getShowUserTweetsRequest) {
        LogManager.getLogger(ClientThread.class).info("GetShowUserTweetsRequest is getting handled");

        LinkedList<SingleTweet> tweets = new LinkedList<>();
        User curUser = authController.getUserWithAuthToken(getShowUserTweetsRequest.getAuthToken());
        for (Tweet tweet : tweetController.getUserTweets(curUser.getId())) {
            tweets.add(new SingleTweet(
                    tweet.getId(),
                    tweet.getCreatorId(),
                    tweet.getText()
            ));
        }
        return new GetShowUserTweetsResponse(tweets);
    }

    @Override
    public Response getShowlist(GetShowlistRequest getShowlistRequest) {
        LogManager.getLogger(ClientThread.class).info("GetShowlistRequest is getting handled");

        User curUser = authController.getUserWithAuthToken(getShowlistRequest.getAuthToken());
        LinkedList<Integer> f1 = socialController.getFollowingsIds(curUser.getId());
        LinkedList<Integer> f2 = socialController.getFollowersIds(curUser.getId());
        LinkedList<Integer> f3 = socialController.getBlacklistIds(curUser.getId());
        LinkedList<SingleUser> following = new LinkedList<>();
        LinkedList<SingleUser> followers = new LinkedList<>();
        LinkedList<SingleUser> blacklist = new LinkedList<>();
        for (Integer id : f1) {
            following.add(new SingleUser(id));
        }
        for (Integer id : f2) {
            followers.add(new SingleUser(id));
        }
        for (Integer id : f3) {
            blacklist.add(new SingleUser(id));
        }
        return new GetShowlistResponse(following, followers, blacklist);
    }

    @Override
    public Response getNotificationPage(GetNotificationPageRequest getNotificationPageRequest) {
        LogManager.getLogger(ClientThread.class).info("GetNotificationPageRequest is getting handled");

        LinkedList<SingleFollowNotification> singleFollowNotifications = new LinkedList<>();
        LinkedList<SingleSystemNotification> singleSystemNotifications = new LinkedList<>();
        GetNotificationPageResponse response = new GetNotificationPageResponse(
                singleFollowNotifications,
                singleSystemNotifications
        );
        return response;
    }

    @Override
    public Response getProfile(GetProfileRequest getProfileRequest) {
        LogManager.getLogger(ClientThread.class).info("GetProfileRequest is getting handled");

        if (getProfileRequest.getUserId() == -1) {
            getProfileRequest.setUserId(authController.getUserWithAuthToken(getProfileRequest.getAuthToken()).getId());
        }
        Profile p = authController.getProfile(getProfileRequest.getUserId());
        return new GetProfileResponse(
                getProfileRequest.getUserId(),
                authController.getUser(getProfileRequest.getUserId()).getUsername(),
                p.getName(),
                p.getLastSeenPrivacy(),
                p.getBio()
        );
    }

    @Override
    public Response getNewGroupAlertData(GetNewGroupAlertData getNewGroupAlertData) {
        LogManager.getLogger(ClientThread.class).info("GetNewGroupAlertData is getting handled");

        LinkedList<Integer> followingIds = socialController.getFollowingsIds(
                authController.getUserWithAuthToken(getNewGroupAlertData.getAuthToken()).getId()
        );
        LinkedList<SingleUser> availableUsers = new LinkedList<>();
        for (Integer id : followingIds) {
            User user = authController.getUser(id);
            availableUsers.add(new SingleUser(
                    id
            ));
        }
        return new OpenNewGroupAlert(availableUsers);
    }

    @Override
    public Response getProfilePic(GetProfilePicRequest getProfilePicRequest) {
        LogManager.getLogger(ClientThread.class).info("GetProfilePicRequest is getting handled");

        return new GetProfilePicResponse(
                getProfilePicRequest.getUserId(),
                fileController.getProfileString(getProfilePicRequest.getUserId())
        );
    }

    @Override
    public Response searchUser(ExplorerSearchRequest explorerSearchRequest) {
        LogManager.getLogger(ClientThread.class).info("ExplorerSearchRequest is getting handled");

        if (authController.getUser(explorerSearchRequest.getText()) != null) {
            return new CheckProfile(authController.getUser(explorerSearchRequest.getText()).getId());
        }
        return new NullResponse("user not found");
    }

    @Override
    public Response searchUser(ExplorerSearchIdRequest explorerSearchIdRequest) {
        LogManager.getLogger(ClientThread.class).info("ExplorerSearchIdRequest is getting handled");

        if (authController.getUser(explorerSearchIdRequest.getUserId()) != null) {
            return new CheckProfile(authController.getUser(explorerSearchIdRequest.getUserId()).getId());
        }
        return new NullResponse("user not found");
    }

    @Override
    public Response fetchMessageDataModel(GetMessageDataModelRequest getMessageDataModelRequest) {
        LogManager.getLogger(ClientThread.class).info("GetMessageDataModelRequest is getting handled");

        LinkedList<Pair<Integer, String>> list = new LinkedList<>();
        LinkedList<Chat> allChats = messageController.getChats(
                authController.getUserWithAuthToken(getMessageDataModelRequest.getAuthToken()).getId()
        );
        for (Chat chat : allChats) {
            list.add(new Pair<>(
                    chat.getId(),
                    chat.getChatName()
            ));
        }
        MessageDataModel messageDataModel = new MessageDataModel(list);
        return new GetMessageDataModelResponse(messageDataModel);
    }

    @Override
    public Response fetchChatModel(GetChatModelRequest getChatModelRequest) {
        LogManager.getLogger(ClientThread.class).info("GetChatModelRequest is getting handled");

        LinkedList<SinglePm> pms = new LinkedList<>();
        for (Pm pm : messageController.getPms(getChatModelRequest.getChatId())) {
            pms.add(new SinglePm(
                    pm.getId(),
                    pm.getUserId(),
                    pm.getPmVerdict(),
                    pm.getText()
            ));
        }
        ChatModel chatModel = new ChatModel(getChatModelRequest.getChatId(), pms);
        return new GetChatModelResponse(chatModel);
    }
}
