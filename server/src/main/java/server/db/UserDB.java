package server.db;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.Profile;
import server.model.User;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class UserDB implements DBSet<User> {
    GsonBuilder builder;

    public UserDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
//        ExclusionStrategy strategy = new ExclusionStrategy() {
//            @Override
//            public boolean shouldSkipField(FieldAttributes f) {
//                if (f.getDeclaringClass().equals(Profile.class) && !f.getName().equals("id")) {
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean shouldSkipClass(Class<?> clazz) {
//                return false;
//            }
//        };
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
//        builder.addSerializationExclusionStrategy(strategy);
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
        File file = new File("src/main/resources/serverdb/users/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/users/" + s));
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
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/users/" + id + ".txt");
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
        File f = new File("src/main/resources/serverdb/users/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/users/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/users/" + s);
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
