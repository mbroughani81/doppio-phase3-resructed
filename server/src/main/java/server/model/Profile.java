package server.model;

import shared.datatype.LastSeenPrivacy;
import shared.datatype.Privacy;

import java.time.LocalDateTime;

public class Profile {
    private int id;

    private String name;
    private String birthday;
    private String email;
    private String phoneNumber;
    private String bio;
    private Privacy privacy;
    private LastSeenPrivacy lastSeenPrivacy;
    private LocalDateTime lastSeen;

    public Profile(String name, String birthday, String email, String phoneNumber, String bio) {
        this.id = -1;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.privacy = Privacy.PUBLIC;
        this.lastSeenPrivacy = LastSeenPrivacy.FOLLOWING;
        this.lastSeen = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public LastSeenPrivacy getLastSeenPrivacy() {
        return lastSeenPrivacy;
    }

    public void setLastSeenPrivacy(LastSeenPrivacy lastSeenPrivacy) {
        this.lastSeenPrivacy = lastSeenPrivacy;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }
}
