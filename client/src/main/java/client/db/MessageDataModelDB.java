package client.db;

import shared.model.MessageDataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.LinkedList;

public class MessageDataModelDB implements DBSet<MessageDataModel> {

    GsonBuilder builder;

    public MessageDataModelDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public MessageDataModel get(String usernameDir, int id) {
        for (MessageDataModel messageDataModel : all(usernameDir)) {
            if (messageDataModel.getId() == id)
                return messageDataModel;
        }
        return null;
    }

    @Override
    public LinkedList<MessageDataModel> all(String usernameDir) {
        LinkedList<MessageDataModel> messageDataModels = new LinkedList<>();
        File file = new File("src/main/resources/clientdb/" + usernameDir + "/messagedatamodels/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/clientdb/" + usernameDir + "/messagedatamodels/" + s));
                messageDataModels.add(gson.fromJson(reader, MessageDataModel.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return messageDataModels;
    }

    @Override
    public int add(String usernameDir, MessageDataModel messageDataModel) {
        int id;
        if (messageDataModel.getId() != -1)
            id = messageDataModel.getId();
        else
            id = nextId(usernameDir);
        messageDataModel.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(messageDataModel);

//        logger.trace("add chat" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/clientdb/" + usernameDir + "/messagedatamodels/" + id + ".txt");
            fileWriter.write(json);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void remove(String usernameDir, int id) {
        File f = new File("src/main/resources/clientdb/" + usernameDir + "/messagedatamodels/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear(String usernameDir) {
        File file = new File("src/main/resources/clientdb/" + usernameDir + "/messagedatamodels/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/clientdb/" + usernameDir + "/messagedatamodels/" + s);
            f.delete();
        }
    }

    @Override
    public void update(String usernameDir, MessageDataModel messageDataModel) {
        remove(usernameDir, messageDataModel.getId());
        add(usernameDir, messageDataModel);
    }

    @Override
    public int nextId(String usernameDir) {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (MessageDataModel messageDataModel : all(usernameDir)) {
                if (messageDataModel.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
