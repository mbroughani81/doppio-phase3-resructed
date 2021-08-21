package server.dbcontroller;

import org.apache.logging.log4j.LogManager;
import server.model.Session;
import shared.model.AuthToken;

import java.util.Random;

public class SessionController extends AbstractController {
    public Session setUserSession(String username) {
        AuthController authController = new AuthController();
        Random rand = new Random();

        for (Session session : context.Sessions.all()) {
            if (session.getUserId() == authController.getUser(username).getId()) {
                Session session1 = new Session(session.getUserId(), new AuthToken(rand.nextLong()));
                session1.setId(session.getId());
                context.Sessions.update(session1);
                return session1;
            }
        }
        Session session = new Session(
                authController.getUser(username).getId(),
                new AuthToken(rand.nextLong())
        );
        int id = context.Sessions.add(session);

        LogManager.getLogger(SessionController.class).info("new session is created with id : " + id);
        return context.Sessions.get(id);
    }

    public Session getUserSession(String username) {
        AuthController authController = new AuthController();
        for (Session session : context.Sessions.all()) {
            if (session.getUserId() == authController.getUser(username).getId())
                return session;
        }
        return null;
    }

    public Session getUserSession(AuthToken authToken) {
        for (Session session : context.Sessions.all()) {
            if (session.getAuthToken().getVal() == authToken.getVal()) {
                return session;
            }
        }
        return null;
    }
}
