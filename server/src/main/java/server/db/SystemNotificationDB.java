package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.SystemNotification;

import java.io.*;
import java.util.LinkedList;

public class SystemNotificationDB implements DBSet<SystemNotification> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public SystemNotificationDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public SystemNotification get(int id) {
        for (SystemNotification systemNotification : all()) {
            if (systemNotification.getId() == id)
                return systemNotification;
        }
        return null;
    }

    @Override
    public LinkedList<SystemNotification> all() {
        LinkedList<SystemNotification> systemNotifications = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getSystemnotificationroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getSystemnotificationroot() + s));
                systemNotifications.add(gson.fromJson(reader, SystemNotification.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return systemNotifications;
    }

    @Override
    public int add(SystemNotification systemNotification) {
        int id;
        if (systemNotification.getId() != -1)
            id = systemNotification.getId();
        else
            id = nextId();
        systemNotification.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(systemNotification);

//        logger.trace("add systemnotification" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getSystemnotificationroot() + id + ".txt");
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
//        logger.trace("remove systemnotification " + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getSystemnotificationroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getSystemnotificationroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getSystemnotificationroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(SystemNotification systemNotification) {
//        logger.trace("update systemnotification " + systemNotification.getId());

        remove(systemNotification.getId());
        add(systemNotification);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (SystemNotification systemNotification : all()) {
                if (systemNotification.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
