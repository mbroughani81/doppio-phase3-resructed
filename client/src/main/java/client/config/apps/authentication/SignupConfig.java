package client.config.apps.authentication;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SignupConfig {
    private String loginButtonText;
    private String signupButtonText;

    public SignupConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getSignupConfigPath());
        properties.load(fileReader);

        loginButtonText = properties.getProperty("loginButtonText");
        signupButtonText = properties.getProperty("signupButtonText");
    }

    public String getLoginButtonText() {
        return loginButtonText;
    }

    public String getSignupButtonText() {
        return signupButtonText;
    }
}
