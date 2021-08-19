package shared.request;

import shared.response.CheckConnectionResponse;
import shared.response.NullResponse;
import shared.response.Response;

public class CheckConnection extends Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
//        return new CheckConnectionResponse();
        return requestHandler.updateUserOnline(this);
    }
}
