package model;

import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    private Integer id;
    @Column(name = "note",nullable = false)
    private String note;
    @Column(name = "dateTimeCreate",nullable = false)
    private Timestamp dateTimeCreate;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getDateTimeCreate() {
        return dateTimeCreate;
    }

    public void setDateTimeCreate(Timestamp dateTimeCreate) {
        this.dateTimeCreate = dateTimeCreate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Note(int id, User user, String note, Timestamp dateTimeCreate) {
        this.id = id;
        this.user = user;
        this.note = note;
        this.dateTimeCreate = dateTimeCreate;
    }

    public Note(User user, String note, Timestamp dateTimeCreate) {
        this.user = user;
        this.note = note;
        this.dateTimeCreate = dateTimeCreate;
    }

    public Note() {
    }

    public static Model.Finder<Integer,Note> find = new Model.Finder<Integer,Note>(Integer.class, Note.class);

    public static List<Note> all() {
        return find.all();
    }

    public static List<Note> findInvolving(String username) {
        return find.where().eq("user.username", username).findList();
    }

}
