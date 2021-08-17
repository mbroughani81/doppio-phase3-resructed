package shared.response;


public interface ResponseHandler {
    void checkSignupResponse(SignupResponse signupResponse);
    void checkLoginResponse(LoginResponse loginResponse);
    void updateTimeline(GetTimelineResponse getTimelineResponse);
    void updateExplorer(GetExplorerResponse getExplorerResponse);
    void updateTweetPage(GetTweetPageResponse getTweetPageResponse);
    void updateShowTweetsPage(GetShowUserTweetsResponse getShowUserTweetsResponse);
    void updateShowlistPage(GetShowlistResponse getShowlistResponse);
    void updateNotificationPage(GetNotificationPageResponse getNotificationPageResponse);
    void updateProfile(GetProfileResponse getProfileResponse);
    void checkProfile(CheckProfile checkProfile);
    void openNewGroupAlert(OpenNewGroupAlert openNewGroupAlert);
    void updateMessageDataModelDB(GetMessageDataModelResponse getMessageDataModelResponse);
    void updateChatModelDB(GetChatModelResponse getChatModelResponse);
    void updateProfilePic(GetProfilePicResponse getProfilePicResponse);
    void updateTweetPic(GetTweetPicResponse getTweetPicResponse);
    void updatePmPic(GetPmPicResponse getPmPicResponse);
    void socketConnectionIsDown(DisconnectResponse disconnectResponse);
    void socketConnectionInUp(CheckConnectionResponse checkConnectionResponse);
}
