package model;

public class UserAccount {
    private String name;
    private String email;
    private String nic;
    private String password;
    private String accessibility;

    public UserAccount(String name, String email, String nic, String password, String accessibility) {
        this.name = name;
        this.email = email;
        this.nic = nic;
        this.password = password;
        this.accessibility = accessibility;// Active or Inactive user status
    }

    //Getter Methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNic() {
        return nic;
    }

    public String getPassword() {
        return password;
    }

    public String getAccessibility() {
        return accessibility;
    }

    //Setter Methods
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }
}
