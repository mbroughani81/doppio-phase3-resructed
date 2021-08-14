package shared.request;

import shared.response.Response;

public class GetShowlistRequest extends Request {

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getShowlist(this);
    }
}
