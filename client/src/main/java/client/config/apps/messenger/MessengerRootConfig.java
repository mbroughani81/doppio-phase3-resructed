package client.config.apps.messenger;

import client.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MessengerRootConfig {

    private String newgroupButtonText;
    private String newgroupgetgroupnameheadertext;
    private String getgroupnameFxmlFilename;
    private String newgroupselectmembersheadertext;
    private String getgroupmembersFxmlFilename;

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

        newgroupButtonText = properties.getProperty("newgroupButtonText");
        newgroupgetgroupnameheadertext = properties.getProperty("newgroupgetgroupnameheadertext");
        getgroupnameFxmlFilename = properties.getProperty("getgroupnameFxmlFilename");
        newgroupselectmembersheadertext = properties.getProperty("newgroupselectmembersheadertext");
        getgroupmembersFxmlFilename = properties.getProperty("getgroupmembersFxmlFilename");
    }

    public String getNewgroupButtonText() {
        return newgroupButtonText;
    }

    public String getNewgroupgetgroupnameheadertext() {
        return newgroupgetgroupnameheadertext;
    }

    public String getGetgroupnameFxmlFilename() {
        return getgroupnameFxmlFilename;
    }

    public String getGetgroupmembersFxmlFilename() {
        return getgroupmembersFxmlFilename;
    }

    public String getNewgroupselectmembersheadertext() {
        return newgroupselectmembersheadertext;
    }
}
