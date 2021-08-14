package shared.request;

import shared.response.NullResponse;
import shared.response.Response;

public class SignupRequest extends Request {

    private String name;
    private String username;
    private String password;
    private String birthday;
    private String email;
    private String phone;
    private String bio;

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.signupUser(this);
    }

    public SignupRequest(String name,
                         String username,
                         String password,
                         String birthday,
                         String email,
                         String phone,
                         String bio) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
