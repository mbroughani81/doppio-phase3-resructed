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
}
