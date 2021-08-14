package client.db;

import shared.model.SessionModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.LinkedList;

public class SessionModelDB implements DBSet<SessionModel> {

    GsonBuilder builder;

    public SessionModelDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public SessionModel get(String usernameDir, int id) {
        for (SessionModel sessionModel : all(usernameDir)) {
            if (sessionModel.getId() == id)
                return sessionModel;
        }
        return null;
    }

    @Override
    public LinkedList<SessionModel> all(String usernameDir) {
        LinkedList<SessionModel> sessionModels = new LinkedList<>();
        File file = new File("src/main/resources/clientdb/" + usernameDir + "/sessionmodels/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/clientdb/" + usernameDir + "/sessionmodels/" + s));
                sessionModels.add(gson.fromJson(reader, SessionModel.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sessionModels;
    }

    @Override
    public int add(String usernameDir, SessionModel sessionModel) {
        int id;
        if (sessionModel.getId() != -1)
            id = sessionModel.getId();
        else
            id = nextId(usernameDir);
        sessionModel.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(sessionModel);

//        logger.trace("add chat" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/clientdb/" + usernameDir + "/sessionmodels/" + id + ".txt");
            fileWriter.write(json);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void remove(String usernameDir, int id) {
        File f = new File("src/main/resources/clientdb/" + usernameDir + "/sessionmodels/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear(String usernameDir) {
        File file = new File("src/main/resources/clientdb/" + usernameDir + "/sessionmodels/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/clientdb/" + usernameDir + "/sessionmodels/" + s);
            f.delete();
        }
    }

    @Override
    public void update(String usernameDir, SessionModel sessionModel) {
        remove(usernameDir, sessionModel.getId());
        add(usernameDir, sessionModel);
    }

    @Override
    public int nextId(String usernameDir) {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (SessionModel sessionModel : all(usernameDir)) {
                if (sessionModel.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
