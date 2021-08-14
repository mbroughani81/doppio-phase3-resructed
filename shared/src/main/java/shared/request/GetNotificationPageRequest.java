package shared.request;

import shared.response.Response;

public class GetNotificationPageRequest extends Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getNotificationPage(this);
    }
}
