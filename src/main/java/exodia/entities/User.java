package exodia.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "users")
@Table
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;

    public User() {

    }

    @Column(name = "users",unique = true,updatable = false,nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "passwords",nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "emails",updatable = true)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
