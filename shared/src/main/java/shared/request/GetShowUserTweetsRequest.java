package shared.request;

import shared.response.Response;

public class GetShowUserTweetsRequest extends Request {

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getShowUserTweets(this);
    }
}
