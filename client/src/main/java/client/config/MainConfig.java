package client.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    private String mainConfigPath = "src/main/resources/config/mainConfig.txt";

    private String socketConfigPath;
    private String loginConfigPath;
    private String signupConfigPath;
    private String chatrootConfigPath;
    private String hypsinglepmlabelConfigPath;
    private String simpletextConfigPath;
    private String hypertextConfigPath;
    private String mainpageConfigPath;
    private String messengerrootConfigPath;

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
        chatrootConfigPath = properties.getProperty("chatrootConfigPath");
        hypsinglepmlabelConfigPath = properties.getProperty("hypsinglepmlabelConfigPath");
        simpletextConfigPath = properties.getProperty("simpletextConfigPath");
        hypertextConfigPath = properties.getProperty("hypertextConfigPath");
        mainpageConfigPath = properties.getProperty("mainpageConfigPath");
        messengerrootConfigPath = properties.getProperty("messengerrootConfigPath");
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

    public String getChatrootConfigPath() {
        return chatrootConfigPath;
    }

    public String getHypsinglepmlabelConfigPath() {
        return hypsinglepmlabelConfigPath;
    }

    public String getSimpletextConfigPath() {
        return simpletextConfigPath;
    }

    public String getHypertextConfigPath() {
        return hypertextConfigPath;
    }

    public String getMainpageConfigPath() {
        return mainpageConfigPath;
    }

    public String getMessengerrootConfigPath() {
        return messengerrootConfigPath;
    }
}
