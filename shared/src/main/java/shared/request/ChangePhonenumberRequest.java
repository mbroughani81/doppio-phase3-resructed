package shared.request;

import shared.response.Response;

public class ChangePhonenumberRequest extends Request {

    private String phonenumber;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.changePhonenumber(this);
    }

    public ChangePhonenumberRequest(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
