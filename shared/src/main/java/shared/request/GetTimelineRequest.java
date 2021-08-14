package shared.request;

import shared.response.Response;

public class GetTimelineRequest extends Request {

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getTimeline(this);
    }
}
