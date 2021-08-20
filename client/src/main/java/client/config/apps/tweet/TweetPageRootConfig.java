package client.config.apps.tweet;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TweetPageRootConfig {

    private String singleTweetLabelFxmlFilename;

    public TweetPageRootConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getTweetpagerootConfigPath());
        properties.load(fileReader);

        singleTweetLabelFxmlFilename = properties.getProperty("singleTweetLabelFxmlFilename");
    }

    public String getSingleTweetLabelFxmlFilename() {
        return singleTweetLabelFxmlFilename;
    }
}
