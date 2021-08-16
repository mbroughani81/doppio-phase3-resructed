package client.config.apps.chat;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HypSinglePmLabelConfig {
    private String offlineColor;
    private String sentColor;
    private String notseenColor;
    private String seenColor;
    private int profileSize;

    public HypSinglePmLabelConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getHypsinglepmlabelConfigPath());
        properties.load(fileReader);

        offlineColor = properties.getProperty("offlineColor");
        sentColor = properties.getProperty("sentColor");
        notseenColor = properties.getProperty("notseenColor");
        seenColor = properties.getProperty("seenColor");
        profileSize = Integer.parseInt(properties.getProperty("profileSize"));
    }

    public String getOfflineColor() {
        return offlineColor;
    }

    public String getSentColor() {
        return sentColor;
    }

    public String getNotseenColor() {
        return notseenColor;
    }

    public String getSeenColor() {
        return seenColor;
    }

    public int getProfileSize() {
        return profileSize;
    }
}
