package server.config.dbConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {

    private String dbroot;
    private String blocklistroot;
    private String chatroot;
    private String followerlistroot;
    private String followinglistroot;
    private String followrequestroot;
    private String likedtweetlistroot;
    private String messagedataroot;
    private String muteduserlistroot;
    private String notificationboxroot;
    private String pmroot;
    private String profileroot;
    private String reportedtweetlistroot;
    private String scheduledpmroot;
    private String sessionroot;
    private String systemnotificationroot;
    private String tweetroot;
    private String userroot;
    private String usertyperoot;

    public DBConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getDbConfigPath());
        properties.load(fileReader);

        dbroot = properties.getProperty("dbroot");
        blocklistroot = properties.getProperty("blocklistroot");
        chatroot = properties.getProperty("chatroot");
        followerlistroot = properties.getProperty("followerlistroot");
        followinglistroot = properties.getProperty("followinglistroot");
        followrequestroot = properties.getProperty("followrequestroot");
        likedtweetlistroot = properties.getProperty("likedtweetlistroot");
        messagedataroot = properties.getProperty("messagedataroot");
        muteduserlistroot = properties.getProperty("muteduserlistroot");
        notificationboxroot = properties.getProperty("notificationboxroot");
        pmroot = properties.getProperty("pmroot");
        profileroot = properties.getProperty("profileroot");
        reportedtweetlistroot = properties.getProperty("reportedtweetlistroot");
        scheduledpmroot = properties.getProperty("scheduledpmroot");
        sessionroot = properties.getProperty("sessionroot");
        systemnotificationroot = properties.getProperty("systemnotificationroot");
        tweetroot = properties.getProperty("tweetroot");
        userroot = properties.getProperty("userroot");
        usertyperoot = properties.getProperty("usertyperoot");
    }

    public String getDbroot() {
        return dbroot;
    }

    public String getBlocklistroot() {
        return blocklistroot;
    }

    public String getChatroot() {
        return chatroot;
    }

    public String getFollowerlistroot() {
        return followerlistroot;
    }

    public String getFollowinglistroot() {
        return followinglistroot;
    }

    public String getFollowrequestroot() {
        return followrequestroot;
    }

    public String getLikedtweetlistroot() {
        return likedtweetlistroot;
    }

    public String getMessagedataroot() {
        return messagedataroot;
    }

    public String getMuteduserlistroot() {
        return muteduserlistroot;
    }

    public String getNotificationboxroot() {
        return notificationboxroot;
    }

    public String getPmroot() {
        return pmroot;
    }

    public String getProfileroot() {
        return profileroot;
    }

    public String getReportedtweetlistroot() {
        return reportedtweetlistroot;
    }

    public String getScheduledpmroot() {
        return scheduledpmroot;
    }

    public String getSessionroot() {
        return sessionroot;
    }

    public String getSystemnotificationroot() {
        return systemnotificationroot;
    }

    public String getTweetroot() {
        return tweetroot;
    }

    public String getUserroot() {
        return userroot;
    }

    public String getUsertyperoot() {
        return usertyperoot;
    }
}
