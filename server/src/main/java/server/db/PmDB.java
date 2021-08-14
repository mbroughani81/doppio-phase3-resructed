package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.model.Pm;

import java.io.*;
import java.util.LinkedList;

public class PmDB implements DBSet<Pm> {

    GsonBuilder builder;

    public PmDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public Pm get(int id) {
        for (Pm pm : all()) {
            if (pm.getId() == id)
                return pm;
        }
        return null;
    }

    @Override
    public LinkedList<Pm> all() {
        LinkedList<Pm> pms = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/pms/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/pms/" + s));
                pms.add(gson.fromJson(reader, Pm.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pms;
    }

    @Override
    public int add(Pm pm) {
        int id;
        if (pm.getId() != -1)
            id = pm.getId();
        else
            id = nextId();
        pm.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(pm);

//        logger.trace("add pm" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/pms/" + id + ".txt");
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
//        logger.trace("add pm " + id);

        File f = new File("src/main/resources/serverdb/pms/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/pms/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/pms/" + s);
            f.delete();
        }
    }

    @Override
    public void update(Pm pm) {
//        logger.trace("update pm " + pm.getId());

        remove(pm.getId());
        add(pm);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (Pm pm : all()) {
                if (pm.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
