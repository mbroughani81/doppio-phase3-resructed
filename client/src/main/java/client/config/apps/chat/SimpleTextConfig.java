package client.config.apps.chat;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SimpleTextConfig {
    private int fontSize;
    private String color;

    public SimpleTextConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getSimpletextConfigPath());
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
