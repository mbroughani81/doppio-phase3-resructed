package shared.request;

import shared.response.Response;

public class GetMessageDataModelRequest extends Request {

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.fetchMessageDataModel(this);
    }

    public GetMessageDataModelRequest() {
    }
}
