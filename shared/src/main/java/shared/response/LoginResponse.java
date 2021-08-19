package shared.response;

import shared.model.AuthToken;
import shared.model.SessionModel;

import java.util.LinkedList;

public class LoginResponse implements Response {

    private SessionModel sessionModel;
    private LinkedList<String> errors;

    @Override
    public void handle(ResponseHandler responseHandler) {
        responseHandler.checkLoginResponse(this);
    }

    public LoginResponse(SessionModel sessionModel, LinkedList<String> errors) {
        this.sessionModel = sessionModel;
        this.errors = errors;
    }

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public void setSessionModel(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public LinkedList<String> getErrors() {
        return errors;
    }

    public void setErrors(LinkedList<String> errors) {
        this.errors = errors;
    }
}
