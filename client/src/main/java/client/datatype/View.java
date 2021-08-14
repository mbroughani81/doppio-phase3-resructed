package client.datatype;

public enum View {
    LOGIN("login.fxml"),
    SIGNUP("signup.fxml"),
    MAINPAGE("mainpage.fxml"),
    SETTING("settingroot.fxml"),
    PERSONALPAGE("personalpageroot.fxml"),
    MESSENGER("messengerroot.fxml"),
    CHAT("chatroot.fxml"),
    TIMELINE("tweetroot.fxml"),
    NEWTWEET("newtweetroot.fxml"),
    SHOWTWEETS("tweetroot.fxml"),
    SHOWLIST("showlistroot.fxml"),
    NOTIFICATIONPAGE("notificationspageroot.fxml"),
    PROFILEPAGE("profilepageroot.fxml"),
    EDITPROFILEPAGE("editprofilepageroot.fxml"),
//    TWEET("tweetroot.fxml"),
    TWEETPAGE("tweetpageroot.fxml"),
    EXPLORER("explorerroot.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
