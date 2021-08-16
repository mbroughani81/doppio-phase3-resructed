package client.config.apps.mainpage;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainPageConfig {
    private String connectionVerdictConnectedColor;
    private String connectionVerdictDisconnectedColor;
    private String backButtonText;
    private String mainpageButtonText;
    private String exitButtonText;
    private String personalpageButtonText;
    private String timelineButtonText;
    private String messengerButtonText;
    private String settingButtonText;

    public MainPageConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getMainpageConfigPath());
        properties.load(fileReader);

        connectionVerdictConnectedColor = properties.getProperty("connectionVerdictConnectedColor");
        connectionVerdictDisconnectedColor = properties.getProperty("connectionVerdictDisconnectedColor");
        backButtonText = properties.getProperty("backButtonText");
        mainpageButtonText = properties.getProperty("mainpageButtonText");
        exitButtonText = properties.getProperty("exitButtonText");
        personalpageButtonText = properties.getProperty("personalpageButtonText");
        timelineButtonText = properties.getProperty("timelineButtonText");
        messengerButtonText = properties.getProperty("messengerButtonText");
        settingButtonText = properties.getProperty("settingButtonText");
    }

    public String getConnectionVerdictConnectedColor() {
        return connectionVerdictConnectedColor;
    }

    public String getConnectionVerdictDisconnectedColor() {
        return connectionVerdictDisconnectedColor;
    }

    public String getBackButtonText() {
        return backButtonText;
    }

    public String getMainpageButtonText() {
        return mainpageButtonText;
    }

    public String getExitButtonText() {
        return exitButtonText;
    }

    public String getPersonalpageButtonText() {
        return personalpageButtonText;
    }

    public String getTimelineButtonText() {
        return timelineButtonText;
    }

    public String getMessengerButtonText() {
        return messengerButtonText;
    }

    public String getSettingButtonText() {
        return settingButtonText;
    }
}
