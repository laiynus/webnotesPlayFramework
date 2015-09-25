package controllers;

import model.Note;
import model.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

import static play.data.Form.form;
import static play.libs.Json.toJson;

import views.html.*;
import play.mvc.Http.*;

import java.util.List;

@Security.Authenticated(Secured.class)
public class Notes extends Controller {

    public static Result index() {
        return ok(index.render(User.find.byId(request().username())));
    }

    public static Result create(String noteText) {
        Note note = Note.create(noteText, request().username());
        return ok(toJson(note));
    }

    public static Result update(Integer idNote, String noteText) {
        if (Secured.isNoteOf(idNote)) {
            return ok(toJson(Note.update(idNote, noteText)));
        } else {
            return forbidden();
        }
    }

    public static Result delete(Integer idNote) {
        if(Secured.isNoteOf(idNote)) {
            Note.find.ref(idNote).delete();
            return ok();
        } else {
            return forbidden();
        }
    }

    public static Result getNotes(String switcher){
        if(switcher.equals("last")){
            return ok(toJson(Note.findLastUserNotes(request().username())));
        }else{
            return ok(toJson((Note.findAllUserNotes(request().username()))));
        }
    }

    public static Result getNote(Integer id){
        return ok(toJson(Note.getNote(id)));
    }

}
