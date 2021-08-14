package shared.request;

import shared.response.NullResponse;
import shared.response.Response;

public class NullRequest extends Request {

    private String text;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return new NullResponse(text);
    }

    public NullRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
