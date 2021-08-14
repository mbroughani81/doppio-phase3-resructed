package client.dbcontroller;

import org.apache.commons.codec.binary.Base64;
import shared.response.GetProfilePicResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class FileModelController extends AbstractModelController {

    private static final Map<String, Instant> lastRequest = new HashMap<>();

    public void updateProfilePic(GetProfilePicResponse getProfilePicResponse) {
//        String filepath = "src/main/resources/clientdb/" + usernameDir + "/profilepics/" +
//                getProfilePicResponse.getUserId() + ".jpg";
//        File file = new File(filepath);
        if (getProfilePicResponse.getImageString() == null)
            return;
//        if (file.exists()) {
//            Path path = Paths.get(filepath);
//            try {
//                BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
//                FileTime ft = attr.lastModifiedTime();
//                Instant now = Instant.now();
//                if (Math.abs(now.compareTo(ft.toInstant())) < 5)
//                    return;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        saveImage(
                getProfilePicResponse.getImageString(),
                "src/main/resources/clientdb/" + usernameDir + "/profilepics/" +
                        getProfilePicResponse.getUserId() + ".jpg"
        );
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
//        System.out.println(filepath + " is getting checked");
        if (!lastRequest.containsKey(filepath)) {
            updateLastRequest(filepath);
            return true;
        }
        Instant curRequest = lastRequest.get(filepath);
//        System.out.println(Math.abs(Instant.now().compareTo(curRequest)));
        if (Math.abs(Duration.between(Instant.now(), curRequest).getSeconds()) > 5) {
            updateLastRequest(filepath);
            return true;
        }
        return false;
    }

    private static void updateLastRequest(String filepath) {
        lastRequest.put(filepath, Instant.now());
    }
}
