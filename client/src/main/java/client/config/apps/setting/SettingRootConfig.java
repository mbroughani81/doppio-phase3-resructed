package client.config.apps.setting;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SettingRootConfig {

    private String privacyButtonText;
    private String lastseenprivacyButtonText;
    private String setactivityButtonText;
    private String changepasswordButtonText;
    private String deleteaccountButtonText;
    private String logoutButtonText;
    private String changepassworddialogContentText;
    private String lastseenchoicesNobodyText;
    private String lastseenchoicesEverybodyText;
    private String lastseenchoicesFollowingText;
    private String changelastseendialogContentText;
    private String privacychoicesPrivateText;
    private String privacychoicesPublicText;
    private String changeprivacydialogContentText;
    private String activityActiveText;
    private String activityDeactiveText;
    private String changeactivitydialogContentText;


    public SettingRootConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getSettingrootConfigPath());
        properties.load(fileReader);

        privacyButtonText = properties.getProperty("privacyButtonText");
        lastseenprivacyButtonText = properties.getProperty("lastseenprivacyButtonText");
        setactivityButtonText = properties.getProperty("setactivityButtonText");
        changepasswordButtonText = properties.getProperty("changepasswordButtonText");
        deleteaccountButtonText = properties.getProperty("deleteaccountButtonText");
        logoutButtonText = properties.getProperty("logoutButtonText");
        changepassworddialogContentText = properties.getProperty("changepassworddialogContentText");
        lastseenchoicesNobodyText = properties.getProperty("lastseenchoicesNobodyText");
        lastseenchoicesEverybodyText = properties.getProperty("lastseenchoicesEverybodyText");
        lastseenchoicesFollowingText = properties.getProperty("lastseenchoicesFollowingText");
        changelastseendialogContentText = properties.getProperty("changelastseendialogContentText");
        privacychoicesPrivateText = properties.getProperty("privacychoicesPrivateText");
        privacychoicesPublicText = properties.getProperty("privacychoicesPublicText");
        changeprivacydialogContentText = properties.getProperty("changeprivacydialogContentText");
        activityActiveText = properties.getProperty("activityActiveText");
        activityDeactiveText = properties.getProperty("activityDeactiveText");
        changeactivitydialogContentText = properties.getProperty("changeactivitydialogContentText");
    }

    public String getPrivacyButtonText() {
        return privacyButtonText;
    }

    public String getLastseenprivacyButtonText() {
        return lastseenprivacyButtonText;
    }

    public String getSetactivityButtonText() {
        return setactivityButtonText;
    }

    public String getChangepasswordButtonText() {
        return changepasswordButtonText;
    }

    public String getDeleteaccountButtonText() {
        return deleteaccountButtonText;
    }

    public String getLogoutButtonText() {
        return logoutButtonText;
    }

    public String getChangepassworddialogContentText() {
        return changepassworddialogContentText;
    }

    public String getLastseenchoicesNobodyText() {
        return lastseenchoicesNobodyText;
    }

    public String getLastseenchoicesEverybodyText() {
        return lastseenchoicesEverybodyText;
    }

    public String getLastseenchoicesFollowingText() {
        return lastseenchoicesFollowingText;
    }

    public String getChangelastseendialogContentText() {
        return changelastseendialogContentText;
    }

    public String getPrivacychoicesPrivateText() {
        return privacychoicesPrivateText;
    }

    public String getPrivacychoicesPublicText() {
        return privacychoicesPublicText;
    }

    public String getChangeprivacydialogContentText() {
        return changeprivacydialogContentText;
    }

    public String getActivityActiveText() {
        return activityActiveText;
    }

    public String getActivityDeactiveText() {
        return activityDeactiveText;
    }

    public String getChangeactivitydialogContentText() {
        return changeactivitydialogContentText;
    }
}
