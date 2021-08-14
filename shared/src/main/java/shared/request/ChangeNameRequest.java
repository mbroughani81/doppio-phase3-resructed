package shared.request;

import shared.response.Response;

public class ChangeNameRequest extends Request {

    private String name;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeName(this);
    }

    public ChangeNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
