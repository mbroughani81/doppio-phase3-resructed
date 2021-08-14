package shared.request;

import shared.response.Response;

public interface RequestHandler {
    Response signupUser(SignupRequest signupRequest);
    Response loginUser(LoginRequest loginRequest);
    Response newPrivateChat(NewPrivateChatRequest newPrivateChatRequest);
    Response newTweet(NewTweetRequest newTweetRequest);
    Response newPm(NewPmRequest newPmRequest);
    Response newFollow(NewFollowRequest newFollowRequest);
    Response newGroup(NewGroupRequest newGroupRequest);
    Response changeBio(ChangeBioRequest changeBioRequest);
    Response changeName(ChangeNameRequest changeNameRequest);
    Response changeEmail(ChangeEmailRequest changeEmailRequest);
    Response changePhonenumber(ChangePhonenumberRequest changePhonenumberRequest);
    Response changeBirthday(ChangeBirthdayRequest changeBirthdayRequest);
    Response changeProfile(ChangeProfileRequest changeProfileRequest);
    Response getTimeline(GetTimelineRequest getTimelineRequest);
    Response getExplorer(GetExplorerRequest getExplorerRequest);
    Response getTweetPage(GetTweetPageRequest getTweetPageRequest);
    Response getShowUserTweets(GetShowUserTweetsRequest getShowUserTweetsRequest);
    Response getShowlist(GetShowlistRequest getShowlistRequest);
    Response getNotificationPage(GetNotificationPageRequest getNotificationPageRequest);
    Response getProfile(GetProfileRequest getProfileRequest);
    Response getNewGroupAlertData(GetNewGroupAlertData getNewGroupAlertData);
    Response getProfilePic(GetProfilePicRequest getProfilePicRequest);
    Response searchUser(ExplorerSearchRequest explorerSearchRequest);
    Response searchUser(ExplorerSearchIdRequest explorerSearchIdRequest);
    Response fetchMessageDataModel(GetMessageDataModelRequest getMessageDataModelRequest);
    Response fetchChatModel(GetChatModelRequest getChatModelRequest);

}
