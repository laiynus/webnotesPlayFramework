package controllers;

import model.Note;
import model.User;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

import java.util.List;

import static play.data.Form.form;


public class Application extends Controller {

    public static class Login {
        public String email;
        public String password;
    }


    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }


    public static Result notesList() {
       return ok(notes.render(Note.all()));
    }

    public static Result login() {
        return ok();
    }

}
