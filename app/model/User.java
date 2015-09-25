package model;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Note> notes;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public static Model.Finder<String, User> find = new Model.Finder<String, User>(String.class, User.class);

    public static User authenticate(String username, String password) {
        return find.where().eq("username", username).eq("password", password).findUnique();
    }

    public static User create(String username, String password) {
        User user = new User(username, password);
        user.save();
        return user;
    }

    public static User getUser(String username){
        return find.where().eq("username",username).findUnique();
    }

}
