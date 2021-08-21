package server.config.dbcontrollerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AuthControllerConfig {

    private String savedmessageChatName;
    private String ghostuserUsername;
    private String lastseenrecentlyText;
    private String onlineText;
    private String lastseenpart1Text;
    private String lastseenpart2Text;

    public AuthControllerConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getAuthcontrollerConfigPath());
        properties.load(fileReader);

        savedmessageChatName = properties.getProperty("savedmessageChatName");
        ghostuserUsername = properties.getProperty("ghostuserUsername");
        lastseenrecentlyText = properties.getProperty("lastseenrecentlyText");
        onlineText = properties.getProperty("onlineText");
        lastseenpart1Text = properties.getProperty("lastseenpart1Text");
        lastseenpart2Text = properties.getProperty("lastseenpart2Text");
    }

    public String getSavedmessageChatName() {
        return savedmessageChatName;
    }

    public String getGhostuserUsername() {
        return ghostuserUsername;
    }

    public String getLastseenrecentlyText() {
        return lastseenrecentlyText;
    }

    public String getOnlineText() {
        return onlineText;
    }

    public String getLastseenpart1Text() {
        return lastseenpart1Text;
    }

    public String getLastseenpart2Text() {
        return lastseenpart2Text;
    }
}
