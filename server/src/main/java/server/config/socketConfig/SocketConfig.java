package server.config.socketConfig;

import server.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SocketConfig {
    private String localhost;
    private int port;

    public SocketConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        MainConfig mainConfig = new MainConfig();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getSocketConfigPath());
        properties.load(fileReader);

        localhost = properties.getProperty("localhost");
        port = Integer.parseInt(properties.getProperty("port"));
    }

    public String getLocalhost() {
        return localhost;
    }

    public int getPort() {
        return port;
    }
}
