package server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {

    private String mainConfigPath = "src/main/resources/config/mainConfig.txt";

    private String socketConfigPath;
    private String dbConfigPath;

    public MainConfig() {
        try {
            setProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfigPath);
        properties.load(fileReader);

        socketConfigPath = properties.getProperty("socketConfigPath");
        dbConfigPath = properties.getProperty("dbConfigPath");
    }

    public String getSocketConfigPath() {
        return socketConfigPath;
    }

    public String getDbConfigPath() {
        return dbConfigPath;
    }
}
