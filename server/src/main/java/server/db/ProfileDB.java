package server.db;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.Profile;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

public class ProfileDB implements DBSet<Profile> {
//    static Logger logger = LogManager.getLogger(ProfileDB.class);

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public ProfileDB() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public Profile get(int id) {
        for (Profile profile : all()) {
            if (profile.getId() == id)
                return profile;
        }
        return null;
    }

    @Override
    public LinkedList<Profile> all() {
        LinkedList<Profile> profiles = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getProfileroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getProfileroot() + s));
                profiles.add(gson.fromJson(reader, Profile.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return profiles;
    }

    @Override
    public int add(Profile profile) {
        int id;
        if (profile.getId() != -1)
            id = profile.getId();
        else
            id = nextId();
        profile.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(profile);

//        logger.trace("add profile" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getProfileroot() + id + ".txt");
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
//        logger.trace("remove profile " + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getProfileroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getProfileroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getProfileroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(Profile profile) {
//        logger.trace("update profile " + profile.getId());

        remove(profile.getId());
        add(profile);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (Profile profile : all()) {
                if (profile.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}