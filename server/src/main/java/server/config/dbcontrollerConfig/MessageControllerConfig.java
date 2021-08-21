package server.config.dbcontrollerConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MessageControllerConfig {

    private String pvChatnameText;
    private String deletedPmText;
    private String forwardedTweetText;
    private String errorChatModelText;


    public MessageControllerConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getMessagecontrollerConfigPath());
        properties.load(fileReader);
        pvChatnameText = properties.getProperty("pvChatnameText");
        deletedPmText = properties.getProperty("deletedPmText");
        forwardedTweetText = properties.getProperty("forwardedTweetText");
        errorChatModelText = properties.getProperty("errorChatModelText");
    }

    public String getPvChatnameText() {
        return pvChatnameText;
    }

    public String getDeletedPmText() {
        return deletedPmText;
    }

    public String getForwardedTweetText() {
        return forwardedTweetText;
    }

    public String getErrorChatModelText() {
        return errorChatModelText;
    }
}
