package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.UserType;

import java.io.*;
import java.util.LinkedList;

public class UserTypeDB implements DBSet<UserType> {

    GsonBuilder builder;

    public UserTypeDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public UserType get(int id) {
        for (UserType userType : all()) {
            if (userType.getId() == id)
                return userType;
        }
        return null;
    }

    @Override
    public LinkedList<UserType> all() {
        LinkedList<UserType> userTypes = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/usertypes/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/usertypes/" + s));
                userTypes.add(gson.fromJson(reader, UserType.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return userTypes;
    }

    @Override
    public int add(UserType userType) {
        int id;
        if (userType.getId() != -1)
            id = userType.getId();
        else
            id = nextId();
        userType.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(userType);

//        logger.trace("add usertype" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/usertypes/" + id + ".txt");
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
//        logger.trace("remove usertype " + id);

        File f = new File("src/main/resources/serverdb/usertypes/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/usertypes/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/usertypes/" + s);
            f.delete();
        }
    }

    @Override
    public void update(UserType userType) {
//        logger.trace("update usertype " + userType.getId());

        remove(userType.getId());
        add(userType);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (UserType userType : all()) {
                if (userType.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
