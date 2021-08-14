package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.MessageData;

import java.io.*;
import java.util.LinkedList;

public class MessageDataDB implements DBSet<MessageData>{

    GsonBuilder builder;

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
        File file = new File("src/main/resources/serverdb/messagedatas/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/messagedatas/" + s));
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
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/messagedatas/" + id + ".txt");
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

        File f = new File("src/main/resources/serverdb/messagedatas/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/messagedatas/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/messagedatas/" + s);
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
