package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.Session;

import java.io.*;
import java.util.LinkedList;

public class SessionDB implements DBSet<Session> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public SessionDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public Session get(int id) {
        for (Session session : all()) {
            if (session.getId() == id)
                return session;
        }
        return null;
    }

    @Override
    public LinkedList<Session> all() {
        LinkedList<Session> sessions = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getSessionroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getSessionroot() + s));
                sessions.add(gson.fromJson(reader, Session.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sessions;
    }

    @Override
    public int add(Session session) {
        int id;
        if (session.getId() != -1)
            id = session.getId();
        else
            id = nextId();
        session.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(session);

//        logger.trace("add session" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getSessionroot() + id + ".txt");
            fileWriter.write(json);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void remove(int id) {
//        logger.trace("remove session " + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getSessionroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getSessionroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getSessionroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(Session session) {
//        logger.trace("update session " + session.getId());

        remove(session.getId());
        add(session);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (Session session : all()) {
                if (session.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
