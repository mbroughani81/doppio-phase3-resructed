package server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    private String mainConfigPath = "src/main/resources/config/mainConfig.txt";

    private String socketConfigPath;
    private String dbConfigPath;
    private String authcontrollerConfigPath;
    private String filecontrollerConfigPath;
    private String messagecontrollerConfigPath;
    private String sessioncontrollerConfigPath;
    private String socialcontrollerConfigPath;
    private String tweetcontrollerConfigPath;

    public MainConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfigPath);
        properties.load(fileReader);

        socketConfigPath = properties.getProperty("socketConfigPath");
        dbConfigPath = properties.getProperty("dbConfigPath");
        authcontrollerConfigPath = properties.getProperty("authcontrollerConfigPath");
        filecontrollerConfigPath = properties.getProperty("filecontrollerConfigPath");
        messagecontrollerConfigPath = properties.getProperty("messagecontrollerConfigPath");
        sessioncontrollerConfigPath = properties.getProperty("sessioncontrollerConfigPath");
        socialcontrollerConfigPath = properties.getProperty("socialcontrollerConfigPath");
        tweetcontrollerConfigPath = properties.getProperty("tweetcontrollerConfigPath");
    }

    public String getSocketConfigPath() {
        return socketConfigPath;
    }

    public String getDbConfigPath() {
        return dbConfigPath;
    }

    public String getAuthcontrollerConfigPath() {
        return authcontrollerConfigPath;
    }

    public String getFilecontrollerConfigPath() {
        return filecontrollerConfigPath;
    }

    public String getMessagecontrollerConfigPath() {
        return messagecontrollerConfigPath;
    }

    public String getSessioncontrollerConfigPath() {
        return sessioncontrollerConfigPath;
    }

    public String getSocialcontrollerConfigPath() {
        return socialcontrollerConfigPath;
    }

    public String getTweetcontrollerConfigPath() {
        return tweetcontrollerConfigPath;
    }
}
