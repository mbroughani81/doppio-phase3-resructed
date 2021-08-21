package server.controller;

import server.config.dbConfig.DBConfig;

import java.io.File;

public class PathCreator {
    public static void createServerResource() {
        DBConfig dbConfig = new DBConfig();
        String[] folderNames = new String[]{
                dbConfig.getBlocklistroot(),
                dbConfig.getChatroot(),
                dbConfig.getFollowerlistroot(),
                dbConfig.getFollowinglistroot(),
                dbConfig.getFollowinglistroot(),
                dbConfig.getFollowrequestroot(),
                dbConfig.getLikedtweetlistroot(),
                dbConfig.getMessagedataroot(),
                dbConfig.getMuteduserlistroot(),
                dbConfig.getNotificationboxroot(),
                dbConfig.getNotificationboxroot(),
                dbConfig.getPmpicroot(),
                dbConfig.getProfilepicroot(),
                dbConfig.getTweetpicroot(),
                dbConfig.getPmroot(),
                dbConfig.getProfileroot(),
                dbConfig.getReportedtweetlistroot(),
                dbConfig.getRetweetedtweetlistroot(),
                dbConfig.getScheduledpmroot(),
                dbConfig.getSessionroot(),
                dbConfig.getSystemnotificationroot(),
                dbConfig.getTweetroot(),
                dbConfig.getUserroot(),
                dbConfig.getUsertyperoot(),
        };
        String resourcesPath = dbConfig.getDbroot();
        for (String str : folderNames) {
            File file = new File(resourcesPath + str );
            file.mkdirs();
        }
    }
}
