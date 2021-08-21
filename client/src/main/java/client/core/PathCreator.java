package client.core;

import java.io.File;

public class PathCreator {
    public static void createClientResource(String username) {
        String[] folderNames = new String[]{"messagedatamodels", "sessionmodels", "chatmodels",
                "profilepics", "pmpics", "tweetpics"};
        String resourcesPath = "src/main/resources/clientdb/";
        for (String str : folderNames) {
            File file = new File(resourcesPath + username + "/" + str);
            file.mkdirs();
        }
    }
}
