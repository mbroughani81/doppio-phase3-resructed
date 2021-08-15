package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.*;
import shared.datatype.ChatType;
import shared.model.AuthToken;
import shared.request.*;

public class AuthController extends AbstractController {
    public void signupUser(SignupRequest signupRequest) {
        Profile profile = new Profile(
                signupRequest.getName(),
                signupRequest.getBirthday(),
                signupRequest.getEmail(),
                signupRequest.getPhone(),
                signupRequest.getBio()
        );
        BlockList blockList = new BlockList();
        FollowerList followerList = new FollowerList();
        FollowingList followingList = new FollowingList();
        MessageData messageData = new MessageData();
        NotificationBox notificationBox = new NotificationBox();
        LikedTweetList likedTweetList = new LikedTweetList();
        ReportedTweetList reportedTweetList = new ReportedTweetList();
        MutedUserList mutedUserList = new MutedUserList();
        int id0 = context.Profiles.add(profile);
        int id1 = context.BlockLists.add(blockList);
        int id2 = context.FollowerLists.add(followerList);
        int id3 = context.FollowingLists.add(followingList);
        int id4 = context.MessageDatas.add(messageData);
        int id5 = context.NotificationBoxes.add(notificationBox);
        int id6 = context.LikedTweetLists.add(likedTweetList);
        int id7 = context.ReportedTweetLists.add(reportedTweetList);
        int id8 = context.MutedUserLists.add(mutedUserList);
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                id0, id1, id2, id3, id4, id5, id6, id7, id8
        );
        context.Users.add(user);
        Chat savedMessageChat = new Chat(user.getId(), ChatType.PRIVATE);
        savedMessageChat.getMemberIds().add(user.getId());
        savedMessageChat.setChatName("Saved Message");
        int savedMessageChatId = context.Chats.add(savedMessageChat);
        savedMessageChat.setParentChatId(savedMessageChatId);
        context.Chats.update(savedMessageChat);
        messageData.getChatIds().add(savedMessageChatId);
        context.MessageDatas.update(messageData);

        LogManager.getLogger(AuthController.class).info("signup is done " + user.toString());
    }

    public void changeName(ChangeNameRequest changeNameRequest) {
        User user = getUserWithAuthToken(changeNameRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setName(changeNameRequest.getName());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("name is changed " + user.toString());
    }

    public void changeBirthday(ChangeBirthdayRequest changeBirthdayRequest) {
        User user = getUserWithAuthToken(changeBirthdayRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setBirthday(changeBirthdayRequest.getBirthday());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("birthday is changed " + user.toString());
    }

    public void changeEmail(ChangeEmailRequest changeEmailRequest) {
        User user = getUserWithAuthToken(changeEmailRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setEmail(changeEmailRequest.getEmail());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("email is changed " + user.toString());
    }

    public void changePhonenumber(ChangePhonenumberRequest changePhonenumberRequest) {
        User user = getUserWithAuthToken(changePhonenumberRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setPhoneNumber(changePhonenumberRequest.getPhonenumber());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("phonenumber is changed " + user.toString());
    }

    public void changeBio(ChangeBioRequest changeBioRequest) {
        User user = getUserWithAuthToken(changeBioRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setBio(changeBioRequest.getBio());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("bio is changed " + user.toString());
    }

    public User getUser(String username) {
        for (User user : context.Users.all()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public User getUser(int userId) {
        return context.Users.get(userId);
    }

    public User getUserWithAuthToken(AuthToken authToken) {
        SessionController sessionController = new SessionController();
        return context.Users.get(sessionController.getUserSession(authToken).getUserId());
    }

    public Profile getProfile(int userId) {
        return context.Profiles.get(userId);
    }
}
