package controllers;

import model.Note;
import model.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

import static play.data.Form.form;

import views.html.*;
import views.html.notes.*;
import play.mvc.Http.*;

@Security.Authenticated(Secured.class)
public class Notes extends Controller {

    public static Result index() {
        return ok(index.render(User.find.byId(request().username()), Note.findLastUserNotes(request().username())));
    }

    public static Result create() {
        Note note = Note.create(form().bindFromRequest().get("note"), request().username());
        return ok(item.render(note));
    }

    public static Result update(Integer idNote) {
        if (Secured.isNoteOf(idNote)) {
            return ok(Json.toJson(Note.update(idNote, form().bindFromRequest().get("noteText"))));
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

}
