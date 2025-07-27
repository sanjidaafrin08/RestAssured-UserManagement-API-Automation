import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserModel {
    private String email;
    private String password;
    private String name;
    private String phone_number;
    private String nid;
    private String role;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(String name, String email, String password, String phone_number, String nid, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.nid = nid;
        this.role = role;
    }

    public UserModel() {
        // Default constructor
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    // Getters if needed
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getNid() {
        return nid;
    }
}
