package client.dbcontroller;

import client.config.dbcontrollerConfig.FileModelControllerConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import shared.response.GetPmPicResponse;
import shared.response.GetProfilePicResponse;
import shared.response.GetTweetPicResponse;
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
        FileModelControllerConfig config = new FileModelControllerConfig();
        String path = config.getDbroot() + usernameDir + "/" + config.getProfilepicsroot() +
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
                path
        );

        LogManager.getLogger(FileModelController.class).info("profile is updeted with id " + getProfilePicResponse.getUserId());
    }

    public void updateTweetPic(GetTweetPicResponse getTweetPicResponse) {
        if (getTweetPicResponse.getImageString() == null)
            return;
        FileModelControllerConfig config = new FileModelControllerConfig();
        String path = config.getDbroot() + usernameDir + "/" + config.getTweetpicsroot() +
                getTweetPicResponse.getTweetId() + ".jpg";
        File file = new File(path);
        if (file.exists()) {
            String str1 = getTweetPicResponse.getImageString();
            String str2 = ImageSerializer.encodeFileToBase64Binary(new File(path));
            if (str1.equals(str2))
                return;
        }
        saveImage(
                getTweetPicResponse.getImageString(),
                path
        );

        LogManager.getLogger(FileModelController.class).info("tweet pic is updeted with id " + getTweetPicResponse.getTweetId());
    }

    public void updatePmPic(GetPmPicResponse getPmPicResponse) {
        if (getPmPicResponse.getImageString() == null)
            return;
        FileModelControllerConfig config = new FileModelControllerConfig();
        String path = config.getDbroot() + usernameDir + "/" + config.getPmpicsroot() +
                getPmPicResponse.getPmId() + ".jpg";
        File file = new File(path);
        if (file.exists()) {
            String str1 = getPmPicResponse.getImageString();
            String str2 = ImageSerializer.encodeFileToBase64Binary(new File(path));
            if (str1.equals(str2))
                return;
        }
        saveImage(
                getPmPicResponse.getImageString(),
                path
        );

        LogManager.getLogger(FileModelController.class).info("pm pic is updeted with id " + getPmPicResponse.getPmId());
    }

    public boolean profileExists(int userId) {
        FileModelControllerConfig config = new FileModelControllerConfig();
        String path = config.getDbroot() + usernameDir + "/" + config.getProfilepicsroot() + +
                userId + ".jpg";
        File file = new File(path);
        return file.exists();
    }

    public boolean tweetExists(int tweetId) {
        FileModelControllerConfig config = new FileModelControllerConfig();
        String path = config.getDbroot() + usernameDir + "/" + config.getTweetpicsroot() + +
                tweetId + ".jpg";
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
        FileModelControllerConfig config = new FileModelControllerConfig();
        if (!lastRequest.containsKey(filepath)) {
            updateLastRequest(filepath);
            return true;
        }
        Instant curRequest = lastRequest.get(filepath);
        if (Math.abs(Duration.between(Instant.now(), curRequest).getSeconds()) > config.getCanupdatesecondlimit()) {
            updateLastRequest(filepath);
            return true;
        }
        return false;
    }

    public String getProfilePicPath(int userId) {
        FileModelControllerConfig config = new FileModelControllerConfig();
        return config.getDbroot() + usernameDir + "/" + config.getProfilepicsroot() + userId + ".jpg";
    }

    public String getTweetPicPath(int tweetId) {
        FileModelControllerConfig config = new FileModelControllerConfig();
        return config.getDbroot() + usernameDir + "/" + config.getTweetpicsroot() + tweetId + ".jpg";
    }

    public String getPmPicPath(int pmId) {
        FileModelControllerConfig config = new FileModelControllerConfig();
        return config.getDbroot() + usernameDir + "/" + config.getPmpicsroot() + pmId + ".jpg";
    }

    public static boolean isBefore(LocalDateTime date, String usernameDir, String p ) {
        FileModelControllerConfig config = new FileModelControllerConfig();
        String pp = config.getDbroot() + usernameDir + "/" + p;
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
}
