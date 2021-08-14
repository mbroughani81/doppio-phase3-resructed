package server.dbcontroller;

import server.db.Context;

public class AbstractController {
    protected Context context;

    public AbstractController() {
        context = new Context();
    }
}