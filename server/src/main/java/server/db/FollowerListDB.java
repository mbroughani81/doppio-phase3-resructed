package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.FollowerList;

import java.io.*;
import java.util.LinkedList;

public class FollowerListDB implements DBSet<FollowerList> {

    GsonBuilder builder;

    public FollowerListDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public FollowerList get(int id) {
        for (FollowerList followerList : all()) {
            if (followerList.getId() == id)
                return followerList;
        }
        return null;
    }

    @Override
    public LinkedList<FollowerList> all() {
        LinkedList<FollowerList> followerLists = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/followerlists/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/followerlists/" + s));
                followerLists.add(gson.fromJson(reader, FollowerList.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return followerLists;
    }

    @Override
    public int add(FollowerList followerList) {
        int id;
        if (followerList.getId() != -1)
            id = followerList.getId();
        else
            id = nextId();
        followerList.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(followerList);

//        logger.trace("add followerList" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/followerlists/" + id + ".txt");
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
//        logger.trace("remove followerList " + id);

        File f = new File("src/main/resources/serverdb/followerlists/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/followerlists/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/followerlists/" + s);
            f.delete();
        }
    }

    @Override
    public void update(FollowerList followerList) {
//        logger.trace("update followerList " + followerList.getId());

        remove(followerList.getId());
        add(followerList);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (FollowerList followerList : all()) {
                if (followerList.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
