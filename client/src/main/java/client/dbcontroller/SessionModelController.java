package client.dbcontroller;

import org.apache.logging.log4j.LogManager;
import shared.model.AuthToken;
import shared.model.SessionModel;

public class SessionModelController extends AbstractModelController {

    public void setNewSession(SessionModel session) {
        context.SessionModels.clear(usernameDir);
        context.SessionModels.add(usernameDir, session);

        LogManager.getLogger(SessionModelController.class).info("new session is added in sessionmodel " + session.getId());
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
