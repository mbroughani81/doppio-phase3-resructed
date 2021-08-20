package client.datatype;

public enum PicType {
    PROFILE("profilepic"),
    TWEET("tweetpic"),
    PM("pmpic");

    private String folderName;

    PicType(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
