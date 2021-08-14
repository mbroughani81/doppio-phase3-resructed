package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.NotificationBox;

import java.io.*;
import java.util.LinkedList;

public class NotificationBoxDB implements DBSet<NotificationBox> {

    GsonBuilder builder;

    public NotificationBoxDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public NotificationBox get(int id) {
        for (NotificationBox notificationBox : all()) {
            if (notificationBox.getId() == id)
                return notificationBox;
        }
        return null;
    }

    @Override
    public LinkedList<NotificationBox> all() {
        LinkedList<NotificationBox> notificationBoxes = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/notificationboxes/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/notificationboxes/" + s));
                notificationBoxes.add(gson.fromJson(reader, NotificationBox.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return notificationBoxes;
    }

    @Override
    public int add(NotificationBox notificationBox) {
        int id;
        if (notificationBox.getId() != -1)
            id = notificationBox.getId();
        else
            id = nextId();
        notificationBox.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(notificationBox);

//        logger.trace("add notificationBox" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/notificationboxes/" + id + ".txt");
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
//        logger.trace("remove notificationBox " + id);

        File f = new File("src/main/resources/serverdb/notificationboxes/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/notificationboxes/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/notificationboxes/" + s);
            f.delete();
        }
    }

    @Override
    public void update(NotificationBox notificationBox) {
//        logger.trace("update notificationBox " + notificationBox.getId());

        remove(notificationBox.getId());
        add(notificationBox);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (NotificationBox notificationBox : all()) {
                if (notificationBox.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
