package server.config.dbcontrollerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AuthControllerConfig {

    private String savedmessageChatName;
    private String ghostuserUsername;

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
    }

    public String getSavedmessageChatName() {
        return savedmessageChatName;
    }

    public String getGhostuserUsername() {
        return ghostuserUsername;
    }
}
