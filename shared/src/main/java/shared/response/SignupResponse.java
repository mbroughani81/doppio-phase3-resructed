package shared.response;

import java.util.LinkedList;
import java.util.List;

public class SignupResponse implements Response {

    private LinkedList<String> errors;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.checkSignupResponse(this);
    }

    public SignupResponse(LinkedList<String> errors) {
        this.errors = errors;
    }

    public LinkedList<String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedList<String> errors) {
        this.errors = errors;
    }
}
