package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.MessageData;

import java.io.*;
import java.util.LinkedList;

public class MessageDataDB implements DBSet<MessageData>{

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public MessageDataDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public MessageData get(int id) {
        for (MessageData messageData : all()) {
            if (messageData.getId() == id)
                return messageData;
        }
        return null;
    }

    @Override
    public LinkedList<MessageData> all() {
        LinkedList<MessageData> messageDatas = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getMessagedataroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getMessagedataroot() + s));
                messageDatas.add(gson.fromJson(reader, MessageData.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return messageDatas;
    }

    @Override
    public int add(MessageData messageData) {
        int id;
        if (messageData.getId() != -1)
            id = messageData.getId();
        else
            id = nextId();
        messageData.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(messageData);

//        logger.trace("add messageData" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getMessagedataroot() + id + ".txt");
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
//        logger.trace("remove messageData " + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getMessagedataroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getMessagedataroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getMessagedataroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(MessageData messageData) {
//        logger.trace("update messageData " + messageData.getId());

        remove(messageData.getId());
        add(messageData);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (MessageData messageData : all()) {
                if (messageData.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
