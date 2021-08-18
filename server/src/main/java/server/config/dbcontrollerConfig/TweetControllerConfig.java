package server.config.dbcontrollerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TweetControllerConfig {

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
    }
}
