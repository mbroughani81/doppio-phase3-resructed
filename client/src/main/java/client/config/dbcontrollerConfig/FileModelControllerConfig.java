package client.config.dbcontrollerConfig;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileModelControllerConfig {
    private String dbroot;
    private String profilepicsroot;
    private String tweetpicsroot;
    private String pmpicsroot;
    private int canupdatesecondlimit;

    public FileModelControllerConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getFilemodelcontrollerConfigPath());
        properties.load(fileReader);

        dbroot = properties.getProperty("dbroot");
        profilepicsroot = properties.getProperty("profilepicsroot");
        tweetpicsroot = properties.getProperty("tweetpicsroot");
        pmpicsroot = properties.getProperty("pmpicsroot");
        canupdatesecondlimit = Integer.parseInt(properties.getProperty("canupdatesecondlimit"));
    }

    public String getDbroot() {
        return dbroot;
    }

    public String getProfilepicsroot() {
        return profilepicsroot;
    }

    public String getTweetpicsroot() {
        return tweetpicsroot;
    }

    public String getPmpicsroot() {
        return pmpicsroot;
    }

    public int getCanupdatesecondlimit() {
        return canupdatesecondlimit;
    }
}
