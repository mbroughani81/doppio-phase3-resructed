package server.config.dbcontrollerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SocialControllerConfig {

    private String requestdeclineText;
    private String followingstartText;
    private String unfollowingstartText;

    public SocialControllerConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getSocialcontrollerConfigPath());
        properties.load(fileReader);

        requestdeclineText = properties.getProperty("requestdeclineText");
        followingstartText = properties.getProperty("followingstartText");
        unfollowingstartText = properties.getProperty("unfollowingstartText");
    }

    public String getRequestdeclineText() {
        return requestdeclineText;
    }

    public String getFollowingstartText() {
        return followingstartText;
    }

    public String getUnfollowingstartText() {
        return unfollowingstartText;
    }
}
