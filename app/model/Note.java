package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
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

    public static List all() {
        return find.all();
    }

    public static Note getNote(Integer id){
        return find.where().eq("id", id).findUnique();
    }

    public static List<Note> findAllUserNotes(String username) {
        return find.where().eq("user.username", username).orderBy("dateTimeCreate desc").findList();
    }

    public static List<Note> findLastUserNotes(String username) {
        return find.where().eq("user.username", username).orderBy("dateTimeCreate desc").setMaxRows(10).findList();
    }

    public static Note create(String noteText, String user) {
        Note note = new Note(User.find.ref(user), noteText,new Timestamp(new java.util.Date().getTime()));
        note.save();
        return note;
    }

    public static boolean isNote(Integer idNote, String user) {
        return find.where().eq("user.username", user).eq("id", idNote).findRowCount() > 0;
    }

    public static Note update(Integer idNote, String noteText) {
        Note note = find.ref(idNote);
        note.setDateTimeCreate(new Timestamp(new java.util.Date().getTime()));
        note.setNote(noteText);
        note.update();
        return note;
    }

}
