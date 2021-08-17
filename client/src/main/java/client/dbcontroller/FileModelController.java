package client.dbcontroller;

import org.apache.commons.codec.binary.Base64;
import shared.response.GetProfilePicResponse;
import shared.util.ImageSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileModelController extends AbstractModelController {

    private static final Map<String, Instant> lastRequest = new HashMap<>();

    public void updateProfilePic(GetProfilePicResponse getProfilePicResponse) {
        if (getProfilePicResponse.getImageString() == null)
            return;
        String path = "src/main/resources/clientdb/" + usernameDir + "/profilepics/" +
                getProfilePicResponse.getUserId() + ".jpg";
        File file = new File(path);
        if (file.exists()) {
            String str1 = getProfilePicResponse.getImageString();
            String str2 = ImageSerializer.encodeFileToBase64Binary(new File(path));
            if (str1.equals(str2))
                return;
        }
        saveImage(
                getProfilePicResponse.getImageString(),
                "src/main/resources/clientdb/" + usernameDir + "/profilepics/" +
                        getProfilePicResponse.getUserId() + ".jpg"
        );
    }

    public boolean profileExists(int userId) {
        String path = "src/main/resources/clientdb/" + usernameDir + "/profilepics/" +
                userId + ".jpg";
        File file = new File(path);
        return file.exists();
    }

    private void saveImage(String imageString, String path) {
        Base64 decoder = new Base64();
        byte[] imgBytes = decoder.decode(imageString);
        FileOutputStream osf = null;
        try {
            osf = new FileOutputStream(new File(path));
            osf.write(imgBytes);
            osf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean canUpdate(String filepath) {
        if (!lastRequest.containsKey(filepath)) {
            updateLastRequest(filepath);
            return true;
        }
        Instant curRequest = lastRequest.get(filepath);
        if (Math.abs(Duration.between(Instant.now(), curRequest).getSeconds()) > 5) {
            updateLastRequest(filepath);
            return true;
        }
        return false;
    }

    public String getProfilePicPath(int userId) {
        return "src/main/resources/clientdb/" + usernameDir + "/profilepics/" + userId + ".jpg";
    }

    public static boolean isBefore(LocalDateTime date, String usernameDir, String p ) {
        String pp = "src/main/resources/clientdb/" + usernameDir + "/" + p;
        File file = new File(pp);
        if (!file.exists())
            return false;
        LocalDateTime fileDate = Instant.ofEpochMilli(file.lastModified())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return date.isBefore(fileDate);
    }

    private static void updateLastRequest(String filepath) {
        lastRequest.put(filepath, Instant.now());
    }
/*
    public static boolean recentlyUpdated(String usernameDir, String p) {
        String pp = "src/main/resources/clientdb/" + usernameDir + "/" + p;
        File file = new File(pp);
        if (!file.exists())
            return false;
        LocalDateTime fileDate = Instant.ofEpochMilli(file.lastModified())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        Duration duration = Duration.between(fileDate, LocalDateTime.now()).abs();
        System.out.println(duration.getSeconds());
        return true;
    }
*/

}
