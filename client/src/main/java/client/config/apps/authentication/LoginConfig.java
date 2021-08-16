package client.config.apps.authentication;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginConfig {
    private String loginButtonText;
    private String signupButtonText;

    public LoginConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getLoginConfigPath());
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
