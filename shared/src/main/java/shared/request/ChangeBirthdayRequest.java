package shared.request;

import shared.response.Response;

public class ChangeBirthdayRequest extends Request {

    private String birthday;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changeBirthday(this);
    }

    public ChangeBirthdayRequest(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
