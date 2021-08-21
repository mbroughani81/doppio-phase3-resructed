package server.config.controllerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ClientThreadConfig {

    private String signupDuplicateUserErrorText;
    private String signupEmptyPasswordErrorText;
    private String signupDuplicateEmailErrorText;
    private String signupDuplicatePhoneErrorText;
    private String loginUserNotFoundErrorText;
    private String loginWrongPasswordErrorText;

    public ClientThreadConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getClientthreadConfigPath());
        properties.load(fileReader);

        signupDuplicateUserErrorText = properties.getProperty("signupDuplicateUserErrorText");
        signupEmptyPasswordErrorText = properties.getProperty("signupEmptyPasswordErrorText");
        signupDuplicateEmailErrorText = properties.getProperty("signupDuplicateEmailErrorText");
        signupDuplicatePhoneErrorText = properties.getProperty("signupDuplicatePhoneErrorText");
        loginUserNotFoundErrorText = properties.getProperty("loginUserNotFoundErrorText");
        loginWrongPasswordErrorText = properties.getProperty("loginWrongPasswordErrorText");
    }

    public String getSignupDuplicateUserErrorText() {
        return signupDuplicateUserErrorText;
    }

    public String getSignupEmptyPasswordErrorText() {
        return signupEmptyPasswordErrorText;
    }

    public String getSignupDuplicateEmailErrorText() {
        return signupDuplicateEmailErrorText;
    }

    public String getSignupDuplicatePhoneErrorText() {
        return signupDuplicatePhoneErrorText;
    }

    public String getLoginUserNotFoundErrorText() {
        return loginUserNotFoundErrorText;
    }

    public String getLoginWrongPasswordErrorText() {
        return loginWrongPasswordErrorText;
    }
}
