package server.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import server.config.dbConfig.DBConfig;
import server.model.BlockList;

import java.io.*;
import java.util.LinkedList;

public class BlockListDB implements DBSet<BlockList> {

    GsonBuilder builder;
    DBConfig dbConfig = new DBConfig();

    public BlockListDB() {
        builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public BlockList get(int id) {
        for (BlockList blockList : all()) {
            if (blockList.getId() == id)
                return blockList;
        }
        return null;
    }

    @Override
    public LinkedList<BlockList> all() {
        LinkedList<BlockList> blockLists = new LinkedList<>();
        File file = new File(dbConfig.getDbroot() + dbConfig.getBlocklistroot());
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader(dbConfig.getDbroot() + dbConfig.getBlocklistroot() + s));
                blockLists.add(gson.fromJson(reader, BlockList.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return blockLists;
    }

    @Override
    public int add(BlockList blockList) {
        int id;
        if (blockList.getId() != -1)
            id = blockList.getId();
        else
            id = nextId();
        blockList.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(blockList);

//        logger.trace("add blockList" + json);

        try {
            FileWriter fileWriter = new FileWriter(dbConfig.getDbroot() + dbConfig.getBlocklistroot() + id + ".txt");
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
//        logger.trace("remove blockList" + id);

        File f = new File(dbConfig.getDbroot() + dbConfig.getBlocklistroot() + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File(dbConfig.getDbroot() + dbConfig.getBlocklistroot());
        for (String s : file.list()) {
            File f = new File(dbConfig.getDbroot() + dbConfig.getBlocklistroot() + s);
            f.delete();
        }
    }

    @Override
    public void update(BlockList blockList) {
//        logger.trace("update blockList " + blockList.getId());

        remove(blockList.getId());
        add(blockList);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (BlockList blockList : all()) {
                if (blockList.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
