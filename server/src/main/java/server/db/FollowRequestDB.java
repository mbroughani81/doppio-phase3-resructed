package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.FollowRequestNotification;

import java.io.*;
import java.util.LinkedList;

public class FollowRequestDB implements DBSet<FollowRequestNotification> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public FollowRequestDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public FollowRequestNotification get(int id) {
        for (FollowRequestNotification followRequestNotification : all()) {
            if (followRequestNotification.getId() == id)
                return followRequestNotification;
        }
        return null;
    }

    @Override
    public LinkedList<FollowRequestNotification> all() {
        LinkedList<FollowRequestNotification> followRequestNotifications = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getFollowrequestroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getFollowrequestroot() + s));
                followRequestNotifications.add(gson.fromJson(reader, FollowRequestNotification.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return followRequestNotifications;
    }

    @Override
    public int add(FollowRequestNotification followRequestNotification) {
        int id;
        if (followRequestNotification.getId() != -1)
            id = followRequestNotification.getId();
        else
            id = nextId();
        followRequestNotification.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(followRequestNotification);

//        logger.trace("add followRequestNotification" + json);


        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getFollowrequestroot() + id + ".txt");
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
//        logger.trace("remove followRequest " + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getFollowrequestroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getFollowrequestroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getFollowrequestroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(FollowRequestNotification followRequestNotification) {
//        logger.trace("remove followRequestNotification " + followRequestNotification.getId());

        remove(followRequestNotification.getId());
        add(followRequestNotification);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (FollowRequestNotification followRequestNotification : all()) {
                if (followRequestNotification.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
