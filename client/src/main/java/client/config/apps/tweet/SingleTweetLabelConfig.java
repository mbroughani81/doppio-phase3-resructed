package client.config.apps.tweet;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SingleTweetLabelConfig {

    private String retweetText;
    private int profileSize;
    private String defaultProfileColor;
    private int tweetPicFitWidth;
    private String savetosavedmessagedItemText;
    private String forwardItemText;
    private String blockItemText;
    private String muteItemText;
    private String reportItemText;

    public SingleTweetLabelConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getSingletweetlabelConfigPath());
        properties.load(fileReader);

        retweetText = properties.getProperty("retweetText");
        profileSize = Integer.parseInt(properties.getProperty("profileSize"));
        defaultProfileColor = properties.getProperty("defaultProfileColor");
        tweetPicFitWidth = Integer.parseInt(properties.getProperty("tweetPicFitWidth"));
        savetosavedmessagedItemText = properties.getProperty("savetosavedmessagedItemText");
        forwardItemText = properties.getProperty("forwardItemText");
        blockItemText = properties.getProperty("blockItemText");
        muteItemText = properties.getProperty("muteItemText");
        reportItemText = properties.getProperty("reportItemText");
    }

    public String getRetweetText() {
        return retweetText;
    }

    public int getProfileSize() {
        return profileSize;
    }

    public String getDefaultProfileColor() {
        return defaultProfileColor;
    }

    public int getTweetPicFitWidth() {
        return tweetPicFitWidth;
    }

    public String getSavetosavedmessagedItemText() {
        return savetosavedmessagedItemText;
    }

    public String getForwardItemText() {
        return forwardItemText;
    }

    public String getBlockItemText() {
        return blockItemText;
    }

    public String getMuteItemText() {
        return muteItemText;
    }

    public String getReportItemText() {
        return reportItemText;
    }
}
