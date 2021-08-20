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
        RetweetedTweetList retweetedTweetList = new RetweetedTweetList();
        ReportedTweetList reportedTweetList = new ReportedTweetList();
        MutedUserList mutedUserList = new MutedUserList();
        int id0 = context.Profiles.add(profile);
        int id1 = context.BlockLists.add(blockList);
        int id2 = context.FollowerLists.add(followerList);
        int id3 = context.FollowingLists.add(followingList);
        int id4 = context.MessageDatas.add(messageData);
        int id5 = context.NotificationBoxes.add(notificationBox);
        int id6 = context.LikedTweetLists.add(likedTweetList);
        int id7 = context.RetweetedTweetLists.add(retweetedTweetList);
        int id8 = context.ReportedTweetLists.add(reportedTweetList);
        int id9 = context.MutedUserLists.add(mutedUserList);
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                id0, id1, id2, id3, id4, id5, id6, id7, id8, id9
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

    public void changeActivity(ChangeActivityRequest changeActivityRequest) {
        User user = getUserWithAuthToken(changeActivityRequest.getAuthToken());
        user.setActive(changeActivityRequest.isActivity());
        context.Users.update(user);

        LogManager.getLogger(AuthController.class).info("activity is changed " + user.toString());
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = getUserWithAuthToken(changePasswordRequest.getAuthToken());
        user.setPassword(changePasswordRequest.getPassword());
        context.Users.update(user);

        LogManager.getLogger(AuthController.class).info("password is changed " + user.toString());
    }

    public void changePrivacy(ChangePrivacyRequest changePrivacyRequest) {
        User user = getUserWithAuthToken(changePrivacyRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setPrivacy(changePrivacyRequest.getPrivacy());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("privacy is changed " + user.toString());
    }

    public void changeLastseen(ChangeLastseenprivacyRequest changeLastseenprivacyRequest) {
        User user = getUserWithAuthToken(changeLastseenprivacyRequest.getAuthToken());
        Profile profile = context.Profiles.get(user.getProfileId());
        profile.setLastSeenPrivacy(changeLastseenprivacyRequest.getLastSeenPrivacy());
        context.Profiles.update(profile);

        LogManager.getLogger(AuthController.class).info("lastseen is changed " + user.toString());
    }

    public void newBlock(NewBlockRequest newBlockRequest) {
        User blocker = getUserWithAuthToken(newBlockRequest.getAuthToken());
        BlockList blockList = context.BlockLists.get(blocker.getBlockListId());
        if (blockList.getList().contains(newBlockRequest.getUserId()))
            return;
        blockList.getList().add(newBlockRequest.getUserId());
        context.BlockLists.update(blockList);
    }

    public void newMute(NewMuteRequest newMuteRequest) {
        User muter = getUserWithAuthToken(newMuteRequest.getAuthToken());
        MutedUserList mutedUserList = context.MutedUserLists.get(muter.getMutedUserListId());
        if (mutedUserList.getUserIds().contains(newMuteRequest.getUserId()))
            return;
        mutedUserList.getUserIds().add(newMuteRequest.getUserId());
        context.MutedUserLists.update(mutedUserList);
    }

    public void deleteUser(DeleteAccountRequest deleteAccountRequest) {
        // delete from blocklist, followerlists, followinglists
        // warning : authtoken may not be valid
        AuthController authController = new AuthController();
        User user = authController.getUser(deleteAccountRequest.getUserId());
        Profile profile = context.Profiles.get(user.getProfileId());
        for (BlockList blockList : context.BlockLists.all()) {
            blockList.getList().remove((Object)user.getId());
            context.BlockLists.update(blockList);
        }
        for (FollowerList followerList : context.FollowerLists.all()) {
            followerList.getList().remove((Object)user.getId());
            context.FollowerLists.update(followerList);
        }
        for (FollowingList followingList : context.FollowingLists.all()) {
            followingList.getList().remove((Object)user.getId());
            context.FollowingLists.update(followingList);
        }
        // change username and profile
        user.setUsername("ghostuser" + user.getId());
        user.setActive(false);
        profile.setEmail(user.getUsername());
        profile.setPhoneNumber(user.getUsername());
        context.Users.update(user);
        context.Profiles.update(profile);
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

    public boolean hasEmail(String email) {
        for (User user : context.Users.all()) {
            Profile p = context.Profiles.get(user.getProfileId());
            if (p.getEmail().equals(email))
                return true;
        }
        return false;
    }


    public boolean hasPhone(String phone) {
        for (User user : context.Users.all()) {
            Profile p = context.Profiles.get(user.getProfileId());
            if (p.getPhoneNumber().equals(phone))
                return true;
        }
        return false;
    }
}
