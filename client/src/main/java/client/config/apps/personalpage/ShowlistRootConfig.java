package client.config.apps.personalpage;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ShowlistRootConfig {


    public ShowlistRootConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getShowlistrootConfigPath());
        properties.load(fileReader);

    }


}
