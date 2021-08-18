package server.db;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.Profile;
import server.model.User;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class UserDB implements DBSet<User> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public UserDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
    }

    @Override
    public User get(int id) {
        for (User user : all()) {
            if (user.getId() == id)
                return  user;
        }
        return null;
    }

    @Override
    public LinkedList<User> all() {
        LinkedList<User> users = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getUserroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getUserroot() + s));
                users.add(gson.fromJson(reader, User.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public int add(User user) {
        int id;
        if (user.getId() != -1)
            id = user.getId();
        else
            id = nextId();
        user.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(user);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getUserroot() + id + ".txt");
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
        File f = new File(dbConfig.getDbroot() + dbConfig.getUserroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getUserroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getUserroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(User user) {
        remove(user.getId());
        add(user);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (User user : all()) {
                if (user.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
