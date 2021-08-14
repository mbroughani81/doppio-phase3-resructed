package shared.request;

import shared.response.Response;

public class ExplorerSearchRequest extends Request{

    private String text;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.searchUser(this);
    }

    public ExplorerSearchRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
