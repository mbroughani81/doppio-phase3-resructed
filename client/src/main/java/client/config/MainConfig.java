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
    private String settingrootConfigPath;
    private String editprofilepagerootConfigPath;
    private String notificationpagerootConfigPath;
    private String personalpagerootConfigPath;
    private String profilepagerootConfigPath;
    private String showlistrootConfigPath;
    private String explorerrootConfigPath;
    private String newtweetrootConfigPath;
    private String tweetpagerootConfigPath;
    private String tweetrootConfigPath;
    private String singletweetlabelConfigPath;
    private String filemodelcontrollerConfigPath;

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
        settingrootConfigPath = properties.getProperty("settingrootConfigPath");
        editprofilepagerootConfigPath = properties.getProperty("editprofilepagerootConfigPath");
        notificationpagerootConfigPath = properties.getProperty("notificationpagerootConfigPath");
        personalpagerootConfigPath = properties.getProperty("personalpagerootConfigPath");
        profilepagerootConfigPath = properties.getProperty("profilepagerootConfigPath");
        showlistrootConfigPath = properties.getProperty("showlistrootConfigPath");
        explorerrootConfigPath = properties.getProperty("explorerrootConfigPath");
        newtweetrootConfigPath = properties.getProperty("newtweetrootConfigPath");
        tweetpagerootConfigPath = properties.getProperty("tweetpagerootConfigPath");
        tweetrootConfigPath = properties.getProperty("tweetrootConfigPath");
        singletweetlabelConfigPath = properties.getProperty("singletweetlabelConfigPath");
        filemodelcontrollerConfigPath = properties.getProperty("filemodelcontrollerConfigPath");
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

    public String getSettingrootConfigPath() {
        return settingrootConfigPath;
    }

    public String getEditprofilepagerootConfigPath() {
        return editprofilepagerootConfigPath;
    }

    public String getNotificationpagerootConfigPath() {
        return notificationpagerootConfigPath;
    }

    public String getPersonalpagerootConfigPath() {
        return personalpagerootConfigPath;
    }

    public String getProfilepagerootConfigPath() {
        return profilepagerootConfigPath;
    }

    public String getShowlistrootConfigPath() {
        return showlistrootConfigPath;
    }

    public String getExplorerrootConfigPath() {
        return explorerrootConfigPath;
    }

    public String getNewtweetrootConfigPath() {
        return newtweetrootConfigPath;
    }

    public String getTweetpagerootConfigPath() {
        return tweetpagerootConfigPath;
    }

    public String getTweetrootConfigPath() {
        return tweetrootConfigPath;
    }

    public String getSingletweetlabelConfigPath() {
        return singletweetlabelConfigPath;
    }

    public String getFilemodelcontrollerConfigPath() {
        return filemodelcontrollerConfigPath;
    }
}
