package client.config.apps.chat;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HyperTextConfig {
    private int fontSize;
    private String color;

    public HyperTextConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getHypertextConfigPath());
        properties.load(fileReader);

        fontSize = Integer.parseInt(properties.getProperty("fontSize"));
        color = properties.getProperty("color");
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getColor() {
        return color;
    }
}
