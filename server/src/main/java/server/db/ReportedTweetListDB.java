package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.ReportedTweetList;

import java.io.*;
import java.util.LinkedList;

public class ReportedTweetListDB implements DBSet<ReportedTweetList> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public ReportedTweetListDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public ReportedTweetList get(int id) {
        for (ReportedTweetList likedTweetList : all()) {
            if (likedTweetList.getId() == id)
                return likedTweetList;
        }
        return null;
    }

    @Override
    public LinkedList<ReportedTweetList> all() {
        LinkedList<ReportedTweetList> reportedTweetLists = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getReportedtweetlistroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getReportedtweetlistroot() + s));
                reportedTweetLists.add(gson.fromJson(reader, ReportedTweetList.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return reportedTweetLists;
    }

    @Override
    public int add(ReportedTweetList reportedTweetList) {
        int id;
        if (reportedTweetList.getId() != -1)
            id = reportedTweetList.getId();
        else
            id = nextId();
        reportedTweetList.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(reportedTweetList);

//        logger.trace("add reportedtweetlist" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getReportedtweetlistroot() + id + ".txt");
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
//        logger.trace("remove reportedtweetlist" + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getReportedtweetlistroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getReportedtweetlistroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getReportedtweetlistroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(ReportedTweetList reportedTweetList) {
//        logger.trace("update reportedtweetlist " + reportedTweetList.getId());

        remove(reportedTweetList.getId());
        add(reportedTweetList);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (ReportedTweetList reportedTweetList : all()) {
                if (reportedTweetList.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
