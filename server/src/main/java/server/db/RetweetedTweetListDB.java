package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.LikedTweetList;
import server.model.RetweetedTweetList;

import java.io.*;
import java.util.LinkedList;

public class RetweetedTweetListDB implements DBSet<RetweetedTweetList> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public RetweetedTweetListDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public RetweetedTweetList get(int id) {
        for (RetweetedTweetList retweetedTweetList : all()) {
            if (retweetedTweetList.getId() == id)
                return retweetedTweetList;
        }
        return null;
    }

    @Override
    public LinkedList<RetweetedTweetList> all() {
        LinkedList<RetweetedTweetList> retweetedTweetLists = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getRetweetedtweetlistroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getRetweetedtweetlistroot() + s));
                retweetedTweetLists.add(gson.fromJson(reader, RetweetedTweetList.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return retweetedTweetLists;
    }

    @Override
    public int add(RetweetedTweetList retweetedTweetList) {
        int id;
        if (retweetedTweetList.getId() != -1)
            id = retweetedTweetList.getId();
        else
            id = nextId();
        retweetedTweetList.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(retweetedTweetList);

//        logger.trace("add likedtweetlist" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getRetweetedtweetlistroot() + id + ".txt");
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

        File f = new File(dbConfig.getDbroot() + dbConfig.getRetweetedtweetlistroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getRetweetedtweetlistroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getRetweetedtweetlistroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(RetweetedTweetList retweetedTweetList) {
//        logger.trace("update likedtweetlist " + likedTweetList.getId());

        remove(retweetedTweetList.getId());
        add(retweetedTweetList);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (RetweetedTweetList retweetedTweetList : all()) {
                if (retweetedTweetList.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
