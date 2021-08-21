package server.config.dbcontrollerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TweetControllerConfig {

    private int maxspamlimit;
    private String noaccesserrorText;

    public TweetControllerConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getTweetcontrollerConfigPath());
        properties.load(fileReader);

        maxspamlimit = Integer.parseInt(properties.getProperty("maxspamlimit"));
        noaccesserrorText = properties.getProperty("noaccesserrorText");
    }

    public int getMaxspamlimit() {
        return maxspamlimit;
    }

    public String getNoaccesserrorText() {
        return noaccesserrorText;
    }
}
