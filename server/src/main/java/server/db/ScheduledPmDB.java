package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.ScheduledPm;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class ScheduledPmDB implements DBSet<ScheduledPm> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public ScheduledPmDB() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public ScheduledPm get(int id) {
        for (ScheduledPm scheduledPm : all()) {
            if (scheduledPm.getId() == id)
                return scheduledPm;
        }
        return null;
    }

    @Override
    public LinkedList<ScheduledPm> all() {
        LinkedList<ScheduledPm> scheduledPms = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getScheduledpmroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getScheduledpmroot() + s));
                scheduledPms.add(gson.fromJson(reader, ScheduledPm.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return scheduledPms;
    }

    @Override
    public int add(ScheduledPm scheduledPm) {
        int id;
        if (scheduledPm.getId() != -1)
            id = scheduledPm.getId();
        else
            id = nextId();
        scheduledPm.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(scheduledPm);

//        logger.trace("add profile" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getScheduledpmroot() + id + ".txt");
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
        File f = new File(dbConfig.getDbroot() + dbConfig.getScheduledpmroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getScheduledpmroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getScheduledpmroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(ScheduledPm scheduledPm) {
        remove(scheduledPm.getId());
        add(scheduledPm);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (ScheduledPm scheduledPm : all()) {
                if (scheduledPm.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
