package server.db;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.Tweet;

import java.io.*;
import java.util.LinkedList;

public class TweetDB implements DBSet<Tweet> {

    GsonBuilder builder;

    public TweetDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
//        ExclusionStrategy strategy = new ExclusionStrategy() {
//            @Override
//            public boolean shouldSkipField(FieldAttributes f) {
//                if (f.getDeclaringClass().equals(User.class) && !f.getName().equals("id"))
//                    return true;
//                return false;
//            }
//
//            @Override
//            public boolean shouldSkipClass(Class<?> clazz) {
//                return false;
//            }
//        };
//        builder.setExclusionStrategies(strategy);
    }

    @Override
    public Tweet get(int id) {
        for (Tweet tweet : all()) {
            if (tweet.getId() == id)
                return tweet;
        }
        return null;
    }

    @Override
    public LinkedList<Tweet> all() {
        LinkedList<Tweet> tweets = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/tweets/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/tweets/" + s));
                tweets.add(gson.fromJson(reader, Tweet.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }

    @Override
    public int add(Tweet tweet) {
        int id;
        if (tweet.getId() != -1)
            id = tweet.getId();
        else
            id = nextId();
        tweet.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(tweet);

//        logger.trace("add tweet" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/tweets/" + id + ".txt");
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
//        logger.trace("remove tweet " + id);

        File f = new File("src/main/resources/serverdb/tweets/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/tweets/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/tweets/" + s);
            f.delete();
        }
    }

    @Override
    public void update(Tweet tweet) {
//        logger.trace("update tweet " + tweet.getId());

        remove(tweet.getId());
        add(tweet);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (Tweet tweet : all()) {
                if (tweet.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
