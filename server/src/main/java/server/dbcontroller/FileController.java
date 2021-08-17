package server.dbcontroller;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import shared.request.ChangeProfileRequest;
import shared.util.ImageSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileController {

    public void updateProfile(int userId, String imageString) {
        saveImage(
                imageString,
                "src/main/resources/serverdb/pics/profile/" + userId + ".jpg"
        );

        LogManager.getLogger(FileController.class).info("profile pic is updated userid : " + userId);
    }

    public void updateTweet(int tweetId, String imageString) {
        saveImage(
                imageString,
                "src/main/resources/serverdb/pics/tweet/" + tweetId + ".jpg"
        );

        LogManager.getLogger(FileController.class).info("tweet pic is updated tweetid : " + tweetId);
    }

    public void updatePm(int pmId, String imageString) {
        saveImage(
                imageString,
                "src/main/resources/serverdb/pics/pm/" + pmId + ".jpg"
        );

        LogManager.getLogger(FileController.class).info("pm pic is updated pmid : " + pmId);
    }

    public String getProfileString(int userId) {
        File file = new File("src/main/resources/serverdb/pics/profile/" + userId + ".jpg");
        if (!file.exists())
            return null;
        return ImageSerializer.encodeFileToBase64Binary(file);
    }

    public String getTweetString(int tweetId) {
        File file = new File("src/main/resources/serverdb/pics/tweet/" + tweetId + ".jpg");
        if (!file.exists())
            return null;
        return ImageSerializer.encodeFileToBase64Binary(file);
    }

    public String getPmString(int pmId) {
        File file = new File("src/main/resources/serverdb/pics/pm/" + pmId + ".jpg");
        if (!file.exists())
            return null;
        return ImageSerializer.encodeFileToBase64Binary(file);
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
}
