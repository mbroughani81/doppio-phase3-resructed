package server.db;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import server.model.Profile;
import shared.gson.LocalDateTimeDeserializer;
import shared.gson.LocalDateTimeSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;

public class ProfileDB implements DBSet<Profile> {
//    static Logger logger = LogManager.getLogger(ProfileDB.class);

    GsonBuilder builder;

    public ProfileDB() {
        builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        builder.setPrettyPrinting();
        builder.serializeNulls();
    }

    @Override
    public Profile get(int id) {
        for (Profile profile : all()) {
            if (profile.getId() == id)
                return profile;
        }
        return null;
    }

    @Override
    public LinkedList<Profile> all() {
        LinkedList<Profile> profiles = new LinkedList<>();
        File file = new File("src/main/resources/serverdb/profiles/");
        Gson gson = builder.create();
        for (String s : file.list()) {
            try {
                JsonReader reader = new JsonReader(new FileReader("src/main/resources/serverdb/profiles/" + s));
                profiles.add(gson.fromJson(reader, Profile.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return profiles;
    }

    @Override
    public int add(Profile profile) {
        int id;
        if (profile.getId() != -1)
            id = profile.getId();
        else
            id = nextId();
        profile.setId(id);
        Gson gson = builder.create();
        String json = gson.toJson(profile);

//        logger.trace("add profile" + json);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/serverdb/profiles/" + id + ".txt");
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
//        logger.trace("remove profile " + id);

        File f = new File("src/main/resources/serverdb/profiles/" + id + ".txt");
        f.delete();
    }

    @Override
    public void clear() {
        File file = new File("src/main/resources/serverdb/profiles/");
        for (String s : file.list()) {
            File f = new File("src/main/resources/serverdb/profiles/" + s);
            f.delete();
        }
    }

    @Override
    public void update(Profile profile) {
//        logger.trace("update profile " + profile.getId());

        remove(profile.getId());
        add(profile);
    }

    @Override
    public int nextId() {
        for (int i = 0; ; i++) {
            boolean isUsed = false;
            for (Profile profile : all()) {
                if (profile.getId() == i)
                    isUsed = true;
            }
            if (!isUsed)
                return i;
        }
    }
}
//class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");
//
//    @Override
//    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
//        return new JsonPrimitive(formatter.format(localDateTime));
//    }
//}
//
//class LocalDateTimeDeserializer implements JsonDeserializer < LocalDateTime > {
//    @Override
//    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//            throws JsonParseException {
//        return LocalDateTime.parse(json.getAsString(),
//                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss").withLocale(Locale.ENGLISH));
//    }
//}