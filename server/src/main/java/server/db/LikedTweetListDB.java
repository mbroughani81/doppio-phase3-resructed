package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.LikedTweetList;

import java.io.*;
import java.util.LinkedList;

public class LikedTweetListDB implements DBSet<LikedTweetList> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public LikedTweetListDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public LikedTweetList get(int id) {
        for (LikedTweetList likedTweetList : all()) {
            if (likedTweetList.getId() == id)
                return likedTweetList;
        }
        return null;
    }

    @Override
    public LinkedList<LikedTweetList> all() {
        LinkedList<LikedTweetList> likedTweetLists = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getLikedtweetlistroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getLikedtweetlistroot() + s));
                likedTweetLists.add(gson.fromJson(reader, LikedTweetList.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return likedTweetLists;
    }

    @Override
    public int add(LikedTweetList likedTweetList) {
        int id;
        if (likedTweetList.getId() != -1)
            id = likedTweetList.getId();
        else
            id = nextId();
        likedTweetList.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(likedTweetList);

//        logger.trace("add likedtweetlist" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getLikedtweetlistroot() + id + ".txt");
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
//        logger.trace("remove likedtweetlist" + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getLikedtweetlistroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getLikedtweetlistroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getLikedtweetlistroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(LikedTweetList likedTweetList) {
//        logger.trace("update likedtweetlist " + likedTweetList.getId());

        remove(likedTweetList.getId());
        add(likedTweetList);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (LikedTweetList likedTweetList : all()) {
                if (likedTweetList.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
