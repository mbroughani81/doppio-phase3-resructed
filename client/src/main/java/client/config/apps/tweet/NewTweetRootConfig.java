package client.config.apps.tweet;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class NewTweetRootConfig {

    private String fileChooserDescription;
    private String formats;

    public NewTweetRootConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getNewtweetrootConfigPath());
        properties.load(fileReader);

        fileChooserDescription = properties.getProperty("fileChooserDescription");
        formats = properties.getProperty("formats");
    }

    public String getFileChooserDescription() {
        return fileChooserDescription;
    }

    public String getFormats() {
        return formats;
    }
}
