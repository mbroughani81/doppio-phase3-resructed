package client.dbcontroller;

import shared.model.AuthToken;
import shared.model.SessionModel;

public class SessionModelController extends AbstractModelController {

    public void setNewSession(String username, AuthToken authToken) {
        SessionModel sessionModel = new SessionModel(username, authToken);
        context.SessionModels.clear(usernameDir);
        context.SessionModels.add(usernameDir, sessionModel);
    }
    public SessionModel getSession() {
        if (usernameDir == null)
            return null;
        for (SessionModel sessionModel : context.SessionModels.all(usernameDir)) {
            if (sessionModel.getUsername().equals(usernameDir))
                return sessionModel;
        }
        return null;
    }

}
