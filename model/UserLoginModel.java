package model;

public class UserLoginModel {
    private String email;
    private String password;

    public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
