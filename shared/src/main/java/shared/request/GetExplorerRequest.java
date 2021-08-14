package shared.request;

import shared.response.Response;

public class GetExplorerRequest extends Request {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.getExplorer(this);
    }
}
