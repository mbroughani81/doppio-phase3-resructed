package shared.request;

import shared.response.Response;

public class GetNewGroupAlertData extends Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getNewGroupAlertData(this);
    }
}
