package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.config.dbcontrollerConfig.SocialControllerConfig;
import server.model.*;
import shared.datatype.Privacy;
import shared.model.SingleFollowNotification;
import shared.model.SingleSystemNotification;
import shared.request.NewAcceptRequest;
import shared.request.NewDeclineRequest;
import shared.request.NewFollowRequest;
import shared.request.NewSilentDeclineRequest;

import java.util.LinkedList;

public class SocialController extends AbstractController {

    public void newFollow(NewFollowRequest newFollowRequest) {
        SocialControllerConfig config = new SocialControllerConfig();
        User follower = context.Users.get(newFollowRequest.getFollowerId());
        User followed = context.Users.get(newFollowRequest.getFollowedId());
        FollowerList followerList = context.FollowerLists.get(followed.getFollowersListId());
        if (followerList.getList().contains(follower.getId()) || followed.getId() == follower.getId())
            return;
        Profile followedProfile = context.Profiles.get(followed.getProfileId());
        if (followedProfile.getPrivacy() == Privacy.PUBLIC) {
            addSystemNotification(followed.getId(), follower.getUsername() + config.getFollowingstartText());
            followerList.getList().add(follower.getId());
            FollowingList followingList = context.FollowingLists.get(follower.getFollowingListId());
            followingList.getList().add(followed.getId());
            context.FollowingLists.update(followingList);
            context.FollowerLists.update(followerList);
        } else {
            addFollowNotification(follower.getId(), followed.getId());
        }

        LogManager.getLogger(SocialController.class).info("new follow is created, follower and followed " +
                follower.toString() + " " + followed.toString());
    }

    public void newFollowForced(NewFollowRequest newFollowRequest) {
        SocialControllerConfig config = new SocialControllerConfig();
        User follower = context.Users.get(newFollowRequest.getFollowerId());
        User followed = context.Users.get(newFollowRequest.getFollowedId());
        FollowerList followerList = context.FollowerLists.get(followed.getFollowersListId());
        if (followerList.getList().contains(follower.getId()) || followed.getId() == follower.getId())
            return;

        addSystemNotification(followed.getId(), follower.getUsername() + config.getFollowingstartText());
        followerList.getList().add(follower.getId());
        FollowingList followingList = context.FollowingLists.get(follower.getFollowingListId());
        followingList.getList().add(followed.getId());
        context.FollowingLists.update(followingList);
        context.FollowerLists.update(followerList);

        LogManager.getLogger(SocialController.class).info("new follow is created, follower and followed " +
                follower.toString() + " " + followed.toString());
    }

    public void addFollowNotification(int followerId, int followedId) {
        // check if not blocked
        User follower = context.Users.get(followerId);
        User followed = context.Users.get(followedId);
//        Profile followedProfile = context.Profiles.get(followed.getProfileId());
        BlockList blockList = context.BlockLists.get(followed.getBlockListId());
        if (blockList.getList().contains(follower.getId()))
            return;
        FollowRequestNotification followRequestNotification = new FollowRequestNotification(
                follower.getUsername(),
                followed.getUsername()
        );
        context.FollowRequests.add(followRequestNotification);

        LogManager.getLogger(SocialController.class).info("new follow is created, follower and followed " +
                follower.toString() + " " + followed.toString());
    }

    public void addSystemNotification(int userId, String text) {
        User user = context.Users.get(userId);
        SystemNotification systemNotification = new SystemNotification(userId, text);
        context.SystemNotifications.add(systemNotification);

        LogManager.getLogger(SocialController.class).info("new system notification is added with id " + systemNotification.getId());
    }

    public void declineRequest(NewDeclineRequest newDeclineRequest) {
        AuthController authController = new AuthController();
        SocialControllerConfig config = new SocialControllerConfig();
        FollowRequestNotification followRequestNotification = context.FollowRequests.get(
                newDeclineRequest.getSingleFollowNotification().getFollowRequestId()
        );
        User follower = authController.getUser(followRequestNotification.getFollowerUsername());
        User followed = authController.getUser(followRequestNotification.getFollowedUsername());
        context.FollowRequests.remove(followRequestNotification.getId());
        addSystemNotification(follower.getId(), config.getRequestdeclineText() + followed.getUsername());

        LogManager.getLogger(SocialController.class).info("request declined with id " + followRequestNotification.getId());
    }

    public void silentDeclineRequest(NewSilentDeclineRequest newSilentDeclineRequest) {
        FollowRequestNotification followRequestNotification = context.FollowRequests.get(
                newSilentDeclineRequest.getSingleFollowNotification().getFollowRequestId()
        );
        context.FollowRequests.remove(followRequestNotification.getId());
        LogManager.getLogger(SocialController.class).info("follow request silent declined with id " + followRequestNotification.getId());

    }

    public void acceptRequest(NewAcceptRequest newAcceptRequest) {
        AuthController authController = new AuthController();
        FollowRequestNotification followRequestNotification = context.FollowRequests.get(
                newAcceptRequest.getSingleFollowNotification().getFollowRequestId()
        );
        User follower = authController.getUser(followRequestNotification.getFollowerUsername());
        User followed = authController.getUser(followRequestNotification.getFollowedUsername());
        newFollowForced(new NewFollowRequest(follower.getId(), followed.getId()));
        context.FollowRequests.remove(followRequestNotification.getId());
        LogManager.getLogger(SocialController.class).info("follow request accpeted with id " + followRequestNotification.getId());
    }

    public LinkedList<SingleFollowNotification> getFollowNotification(int userId) {
        AuthController authController = new AuthController();
        User user = authController.getUser(userId);
        LinkedList<FollowRequestNotification> allRequests = context.FollowRequests.all();
        LinkedList<SingleFollowNotification> result = new LinkedList<>();
        for (FollowRequestNotification followRequest : allRequests) {
            if (followRequest.getFollowerUsername().equals(user.getUsername())) {
                result.add(new SingleFollowNotification(
                        followRequest.getId(),
                        followRequest.getFollowerUsername(),
                        followRequest.getFollowedUsername(),
                        false));
            }
            if (followRequest.getFollowedUsername().equals(user.getUsername())) {
                result.add(new SingleFollowNotification(
                        followRequest.getId(),
                        followRequest.getFollowerUsername(),
                        followRequest.getFollowedUsername(),
                        true));
            }
        }
        return result;
    }

    public LinkedList<SingleSystemNotification> getSystemNotification(int userId) {
        AuthController authController = new AuthController();
        User user = authController.getUser(userId);
        LinkedList<SystemNotification> allRequests = context.SystemNotifications.all();
        LinkedList<SingleSystemNotification> result = new LinkedList<>();
        for (SystemNotification systemNotification : allRequests) {
            if (systemNotification.getUserId() == userId) {
                result.add(new SingleSystemNotification(systemNotification.getText()));
            }
        }
        return result;
    }

    public LinkedList<Integer> getFollowingsIds(int userId) {
        return context.FollowingLists.get(context.Users.get(userId).getFollowingListId()).getList();
    }

    public LinkedList<Integer> getFollowersIds(int userId) {
        return context.FollowerLists.get(context.Users.get(userId).getFollowersListId()).getList();
    }

    public LinkedList<Integer> getBlacklistIds(int userId) {
        return context.BlockLists.get(context.Users.get(userId).getBlockListId()).getList();
    }


}
