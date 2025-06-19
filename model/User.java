package model;

public class User {
    private String name, email, nic, password, accessibility;

    //Constructor to initialize a User object
    public User(String name, String email, String nic, String password, String accessibility) {
        this.name = name;
        this.email = email;
        this.nic = nic;
        this.password = password;
        this.accessibility = accessibility;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getNic() { return nic; }
    public String getPassword() { return password; }
    public String getAccessibility() { return accessibility; }
}
