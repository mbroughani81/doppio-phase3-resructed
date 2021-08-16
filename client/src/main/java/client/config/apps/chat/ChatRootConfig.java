package client.config.apps.chat;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ChatRootConfig {
    private String sendButtonText;

    public ChatRootConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getChatrootConfigPath());
        properties.load(fileReader);

        sendButtonText = properties.getProperty("sendButtonText");
    }

    public String getSendButtonText() {
        return sendButtonText;
    }
}
