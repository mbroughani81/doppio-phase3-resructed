package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.FollowerList;
import server.model.FollowingList;
import server.model.User;
import shared.request.NewFollowRequest;

import java.util.LinkedList;

public class SocialController extends AbstractController {

    public void newFollow(NewFollowRequest newFollowRequest) {
        User follower = context.Users.get(newFollowRequest.getFollowerId());
        User followed = context.Users.get(newFollowRequest.getFollowedId());
        FollowerList followerList = context.FollowerLists.get(followed.getFollowersListId());
        if (followerList.getList().contains(follower.getId()))
            return;
        followerList.getList().add(follower.getId());
        FollowingList followingList = context.FollowingLists.get(follower.getFollowingListId());
        followingList.getList().add(followed.getId());
        context.FollowingLists.update(followingList);
        context.FollowerLists.update(followerList);

        LogManager.getLogger(SocialController.class).info("new follow is created, follower and followed " +
                follower.toString() + " " + followed.toString());
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
