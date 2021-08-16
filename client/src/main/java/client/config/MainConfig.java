package client.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    private String mainConfigPath = "src/main/resources/config/mainConfig.txt";

    private String socketConfigPath;
    private String loginConfigPath;
    private String signupConfigPath;

    public MainConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfigPath);
        properties.load(fileReader);

        socketConfigPath = properties.getProperty("socketConfigPath");
        loginConfigPath = properties.getProperty("loginConfigPath");
        signupConfigPath = properties.getProperty("signupConfigPath");
    }

    public String getSocketConfigPath() {
        return socketConfigPath;
    }

    public String getLoginConfigPath() {
        return loginConfigPath;
    }

    public String getSignupConfigPath() {
        return signupConfigPath;
    }
}
