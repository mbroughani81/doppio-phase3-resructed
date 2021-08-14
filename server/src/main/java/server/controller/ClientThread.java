package server.controller;

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
        while(running) {
            Request request = sender.getRequest();
            sender.sendResponse(request.handle(this));
        }
    }

    @Override
    public Response signupUser(SignupRequest signupRequest) {
        authController.signupUser(signupRequest);
        LinkedList<String> errors = new LinkedList<>();
        errors.add("s1");
        errors.add("s2");
        return new SignupResponse(false, errors);
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = authController.getUser(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            sessionController.setUserSession(loginRequest.getUsername());
            return new LoginResponse(
                    loginRequest.getUsername(),
                    user != null && user.getPassword().equals(loginRequest.getPassword()),
                    sessionController.getUserSession(loginRequest.getUsername()).getAuthToken(),
                    new LinkedList<>()
            );
        }
        return new LoginResponse(
                loginRequest.getUsername(),
                user != null && user.getPassword().equals(loginRequest.getPassword()),
                null,
                new LinkedList<>()
        );
    }

    @Override
    public Response newPrivateChat(NewPrivateChatRequest newPrivateChatRequest) {
        messageController.newPrivateChat(newPrivateChatRequest);
        return new NullResponse("salam");
    }

    @Override
    public Response newTweet(NewTweetRequest newTweetRequest) {
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
        if (newPmRequest.getUserId() == -1) {
            newPmRequest.setUserId(authController.getUserWithAuthToken(newPmRequest.getAuthToken()).getId());
        }
        messageController.sendNewPm(newPmRequest);
        return new NullResponse("salam");
    }

    @Override
    public Response newFollow(NewFollowRequest newFollowRequest) {
        if (newFollowRequest.getFollowerId() == -1) {
            newFollowRequest.setFollowerId(authController.getUserWithAuthToken(newFollowRequest.getAuthToken()).getId());
        }
        socialController.newFollow(newFollowRequest);
        return new NullResponse("newFollow is ok");
    }

    @Override
    public Response newGroup(NewGroupRequest newGroupRequest) {
        if (newGroupRequest.getOwnerId() == -1) {
            newGroupRequest.setOwnerId(authController.getUserWithAuthToken(newGroupRequest.getAuthToken()).getId());
        }
        messageController.newGroupChat(newGroupRequest);
        return new NullResponse("group is created");
    }

    @Override
    public Response changeBio(ChangeBioRequest changeBioRequest) {
        authController.changeBio(changeBioRequest);
        return new NullResponse("bio changed");
    }

    @Override
    public Response changeName(ChangeNameRequest changeNameRequest) {
        authController.changeName(changeNameRequest);
        return new NullResponse("name changed");
    }

    @Override
    public Response changeEmail(ChangeEmailRequest changeEmailRequest) {
        authController.changeEmail(changeEmailRequest);
        return new NullResponse("email changed");
    }

    @Override
    public Response changePhonenumber(ChangePhonenumberRequest changePhonenumberRequest) {
        authController.changePhonenumber(changePhonenumberRequest);
        return new NullResponse("phonenumber changed");
    }

    @Override
    public Response changeBirthday(ChangeBirthdayRequest changeBirthdayRequest) {
        authController.changeBirthday(changeBirthdayRequest);
        return new NullResponse("birthday changed");
    }

    @Override
    public Response changeProfile(ChangeProfileRequest changeProfileRequest) {
        User user = authController.getUserWithAuthToken(changeProfileRequest.getAuthToken());
        FileController fileController = new FileController();
        fileController.updateProfile(user.getId(), changeProfileRequest.getImageString());
        return new NullResponse("profile changed");
    }

    @Override
    public Response getTimeline(GetTimelineRequest getTimelineRequest) {
        LinkedList<Tweet> tweets = tweetController.getTimeline(
                authController.getUserWithAuthToken(getTimelineRequest.getAuthToken()).getUsername()
        );
        LinkedList<SingleTweet> resTweets = new LinkedList<>();
        for (Tweet tweet : tweets) {
            resTweets.add(new SingleTweet(
                    tweet.getId(),
                    tweet.getCreatorId(),
                    tweet.getText()
            ));
        }
        return new GetTimelineResponse(resTweets);
    }

    @Override
    public Response getExplorer(GetExplorerRequest getExplorerRequest) {
        LinkedList<Tweet> tweets = tweetController.getTimeline(
                authController.getUserWithAuthToken(getExplorerRequest.getAuthToken()).getUsername()
        );
        LinkedList<SingleTweet> resTweets = new LinkedList<>();
        for (Tweet tweet : tweets) {
            resTweets.add(new SingleTweet(
                    tweet.getId(),
                    tweet.getCreatorId(),
                    tweet.getText()
            ));
        }
        return new GetExplorerResponse(resTweets);
    }

    @Override
    public Response getTweetPage(GetTweetPageRequest getTweetPageRequest) {
        LinkedList<Tweet> tweets = tweetController.getTimeline(
                authController.getUserWithAuthToken(getTweetPageRequest.getAuthToken()).getUsername()
        );
        LinkedList<SingleTweet> resTweets = new LinkedList<>();
        for (Tweet tweet : tweets) {
            resTweets.add(new SingleTweet(
                    tweet.getId(),
                    tweet.getCreatorId(),
                    tweet.getText()
            ));
        }
        Tweet tweet = tweetController.getTweet(getTweetPageRequest.getTweetId());
        SingleTweet mainTweet = new SingleTweet(
                tweet.getId(),
                tweet.getCreatorId(),
                tweet.getText()
        );
        return new GetTweetPageResponse(getTweetPageRequest.getTweetId(), mainTweet, resTweets);
    }

    @Override
    public Response getShowUserTweets(GetShowUserTweetsRequest getShowUserTweetsRequest) {
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
//        System.out.println("new request with id " + getProfileRequest.getUserId() + " has come!");
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
        return new GetProfilePicResponse(
                getProfilePicRequest.getUserId(),
                fileController.getProfileString(getProfilePicRequest.getUserId())
        );
    }

    @Override
    public Response searchUser(ExplorerSearchRequest explorerSearchRequest) {
        if (authController.getUser(explorerSearchRequest.getText()) != null) {
            return new CheckProfile(authController.getUser(explorerSearchRequest.getText()).getId());
        }
        return new NullResponse("user not found");
    }

    @Override
    public Response searchUser(ExplorerSearchIdRequest explorerSearchIdRequest) {
        if (authController.getUser(explorerSearchIdRequest.getUserId()) != null) {
            return new CheckProfile(authController.getUser(explorerSearchIdRequest.getUserId()).getId());
        }
        return new NullResponse("user not found");
    }

    @Override
    public Response fetchMessageDataModel(GetMessageDataModelRequest getMessageDataModelRequest) {
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
        LinkedList<SinglePm> pms = new LinkedList<>();
//        int userId = authController.getUser("a1").getId();
//        int user2Id = authController.getUser("a2").getId();
//        pms.add(new SinglePm(userId, "salam"));
//        pms.add(new SinglePm(user2Id, "gooz"));
        for (Pm pm : messageController.getPms(getChatModelRequest.getChatId())) {
            pms.add(new SinglePm(
                    pm.getUserId(),
                    pm.getPmVerdict(),
                    pm.getText()
            ));
        }
        ChatModel chatModel = new ChatModel(getChatModelRequest.getChatId(), pms);
        return new GetChatModelResponse(chatModel);
    }
}
