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
    private String editItemText;
    private String deleteItemText;
    private String defaultProfileColor;
    private String profilepicsPath;
    private String pmpicsPath;
    private String editdialogecontenttext;
    private String deletedialogecontenttext;
    private int profileSize;
    private int pmpicfitwidth;


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
        editItemText = properties.getProperty("editItemText");
        deleteItemText = properties.getProperty("deleteItemText");
        defaultProfileColor = properties.getProperty("defaultProfileColor");
        profilepicsPath = properties.getProperty("profilepicsPath");
        pmpicsPath = properties.getProperty("pmpicsPath");
        pmpicfitwidth = Integer.parseInt(properties.getProperty("pmpicfitwidth"));
        editdialogecontenttext = properties.getProperty("editdialogecontenttext");
        deletedialogecontenttext = properties.getProperty("deletedialogecontenttext");
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

    public String getEditItemText() {
        return editItemText;
    }

    public String getDeleteItemText() {
        return deleteItemText;
    }

    public String getDefaultProfileColor() {
        return defaultProfileColor;
    }

    public String getProfilepicsPath() {
        return profilepicsPath;
    }

    public String getPmpicsPath() {
        return pmpicsPath;
    }

    public int getPmpicfitwidth() {
        return pmpicfitwidth;
    }

    public String getEditdialogecontenttext() {
        return editdialogecontenttext;
    }

    public String getDeletedialogecontenttext() {
        return deletedialogecontenttext;
    }
}
