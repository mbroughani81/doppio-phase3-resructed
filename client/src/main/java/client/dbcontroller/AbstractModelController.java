package client.dbcontroller;

import client.db.Context;

public class AbstractModelController {
    protected Context context;
    String usernameDir;

    public AbstractModelController() {
        context = new Context();
    }

    public String getUsernameDir() {
        return usernameDir;
    }

    public void setUsernameDir(String usernameDir) {
        this.usernameDir = usernameDir;
    }
}
