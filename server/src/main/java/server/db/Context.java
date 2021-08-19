package server.db;

import server.model.*;

public class Context {
    public DBSet<Tweet> Tweets = new TweetDB();
    public DBSet<User> Users = new UserDB();
    public DBSet<Profile> Profiles = new ProfileDB();
    public DBSet<BlockList> BlockLists = new BlockListDB();
    public DBSet<FollowingList> FollowingLists = new FollowingListDB();
    public DBSet<FollowerList> FollowerLists = new FollowerListDB();
    public DBSet<MessageData> MessageDatas = new MessageDataDB();
    public DBSet<Chat> Chats = new ChatDB();
    public DBSet<Session> Sessions = new SessionDB();
    public DBSet<Pm> Pms = new PmDB();
    public DBSet<FollowRequestNotification> FollowRequests = new FollowRequestDB();
    public DBSet<NotificationBox> NotificationBoxes = new NotificationBoxDB();
    public DBSet<UserType> UserTypes = new UserTypeDB();
    public DBSet<SystemNotification> SystemNotifications = new SystemNotificationDB();
    public DBSet<LikedTweetList> LikedTweetLists = new LikedTweetListDB();
    public DBSet<ReportedTweetList> ReportedTweetLists = new ReportedTweetListDB();
    public DBSet<MutedUserList> MutedUserLists = new MutedUserListDB();
    public DBSet<ScheduledPm> ScheduledPms = new ScheduledPmDB();
    public DBSet<RetweetedTweetList> RetweetedTweetLists = new RetweetedTweetListDB();
    public void clearDB() {
        Tweets.clear();
        Users.clear();
        Profiles.clear();
        BlockLists.clear();
        FollowingLists.clear();
        FollowerLists.clear();
        MessageDatas.clear();
        Chats.clear();
        Sessions.clear();
        Pms.clear();
        FollowRequests.clear();
        NotificationBoxes.clear();
        UserTypes.clear();
        SystemNotifications.clear();
        LikedTweetLists.clear();
        ReportedTweetLists.clear();
        MutedUserLists.clear();
        ScheduledPms.clear();
        RetweetedTweetLists.clear();
    }
}
