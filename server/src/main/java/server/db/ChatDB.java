package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.Chat;;

import java.io.*;
import java.util.LinkedList;

public class ChatDB implements DBSet<Chat> {

    GsonBuilder builder;

    public ChatDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public Chat get(int id) {
        for (Chat chat : all()) {
            if (chat.getId() == id)
                return chat;
        }
        return null;
    }

    @Override
    public LinkedList<Chat> all() {
        LinkedList<Chat> chats = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/chats/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/chats/" + s));
                chats.add(gson.fromJson(reader, Chat.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return chats;
    }

    @Override
    public int add(Chat chat) {
        int id;
        if (chat.getId() != -1)
            id = chat.getId();
        else
            id = nextId();
        chat.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(chat);

//        logger.trace("add chat" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/chats/" + id + ".txt");
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
//        logger.trace("remove chat " + id);

        File f = new File("src/main/resources/serverdb/chats/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/chats/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/chats/" + s);
            f.delete();
        }
    }

    @Override
    public void update(Chat chat) {
//        logger.trace("update chat " + chat.getId());

        remove(chat.getId());
        add(chat);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (Chat chat : all()) {
                if (chat.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
    
}
