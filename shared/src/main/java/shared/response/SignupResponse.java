package shared.response;

import java.util.LinkedList;
import java.util.List;

public class SignupResponse implements Response {

    boolean isOk;
    LinkedList<String> errors;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.checkSignupResponse(this);
    }

    public SignupResponse(boolean isOk, LinkedList<String> errors) {
        this.isOk = isOk;
        this.errors = errors;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public LinkedList<String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedList<String> errors) {
        this.errors = errors;
    }
}
