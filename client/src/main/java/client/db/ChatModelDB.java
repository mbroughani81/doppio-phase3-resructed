package client.db;

import shared.model.ChatModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.LinkedList;

public class ChatModelDB implements DBSet<ChatModel> {

    GsonBuilder builder;

    public ChatModelDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }
    
    @Override
    public ChatModel get(String usernameDir, int id) {
        for (ChatModel chatModel : all(usernameDir)) {
            if (chatModel.getId() == id)
                return chatModel;
        }
        return null;
    }

    @Override
    public LinkedList<ChatModel> all(String usernameDir) {
        LinkedList<ChatModel> chatModels = new LinkedList<>();
        File file = new File("src/main/resources/clientdb/" + usernameDir + "/chatmodels/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/clientdb/" + usernameDir + "/chatmodels/" + s));
                chatModels.add(gson.fromJson(reader, ChatModel.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return chatModels;
    }

    @Override
    public int add(String usernameDir, ChatModel chatModel) {
        int id;
        if (chatModel.getId() != -1)
            id = chatModel.getId();
        else
            id = nextId(usernameDir);
        chatModel.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(chatModel);

//        logger.trace("add chat" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/clientdb/" + usernameDir + "/chatmodels/" + id + ".txt");
            fileWriter.write(json);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;    }

    @Override
    public void remove(String usernameDir, int id) {
        File f = new File("src/main/resources/clientdb/" + usernameDir + "/chatmodels/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear(String usernameDir) {
        File file = new File("src/main/resources/clientdb/" + usernameDir + "/chatmodels/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/clientdb/" + usernameDir + "/chatmodels/" + s);
            f.delete();
        }
    }

    @Override
    public void update(String usernameDir, ChatModel chatModel) {
        remove(usernameDir, chatModel.getId());
        add(usernameDir, chatModel);
    }

    @Override
    public int nextId(String usernameDir) {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (ChatModel chatModel : all(usernameDir)) {
                if (chatModel.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
