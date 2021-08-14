package client.apps.tweet.view;

import javafx.application.Platform;
import shared.request.GetChatModelRequest;
import shared.request.GetTimelineRequest;
import shared.response.GetTimelineResponse;

import java.util.LinkedList;

public class TimelineController extends TweetController {

    public void updateTimeline(GetTimelineResponse getTimelineResponse) {
        Platform.runLater(() -> tweetRootController.updateTweetList(getTimelineResponse.getTweets()));
    }

    @Override
    public Runnable getUpdateAction() {
        return super.getUpdateAction();
    }

    @Override
    public Runnable getRequestAction() {
        return () -> getListener().listen(new GetTimelineRequest());
    }
}
