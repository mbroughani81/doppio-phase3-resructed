package client.config.apps.messenger;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MessengerRootConfig {

    private String multipmButtonText;
    private String newgroupButtonText;
    private String newtypeButtonText;

    public MessengerRootConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getMessengerrootConfigPath());
        properties.load(fileReader);

        multipmButtonText = properties.getProperty("multipmButtonText");
        newgroupButtonText = properties.getProperty("newgroupButtonText");
        newtypeButtonText = properties.getProperty("newtypeButtonText");
    }

    public String getMultipmButtonText() {
        return multipmButtonText;
    }

    public String getNewgroupButtonText() {
        return newgroupButtonText;
    }

    public String getNewtypeButtonText() {
        return newtypeButtonText;
    }
}
