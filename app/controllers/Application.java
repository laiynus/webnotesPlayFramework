package controllers;

import model.User;
import play.Routes;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.*;

import views.html.*;

import static play.data.Form.*;
import static play.libs.Json.toJson;


public class Application extends Controller {

    public static class Login {
        public String username;
        public String password;

        public String validate() {
            if (User.authenticate(username, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static Result login() {
        if (session().get("username") != null) {
            return redirect(routes.Notes.index());
        } else {
            return ok(login.render(form(Login.class)));
        }
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("username", loginForm.get().username);
            return redirect(routes.Notes.index());
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Application.login());
    }

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Notes.getNotes(),
                        controllers.routes.javascript.Notes.create(),
                        controllers.routes.javascript.Notes.delete(),
                        controllers.routes.javascript.Notes.update(),
                        controllers.routes.javascript.Notes.getNote(),
                        controllers.routes.javascript.Application.registered()
                )
        );
    }

    public static Result join() {
        if (session().get("username") != null) {
            return redirect(routes.Notes.index());
        } else {
            return ok(join.render());
        }
    }

    public static Result registered(String username, String password){
        if(User.getUser(username)!=null){
            return ok(toJson("This user already exist"));
        }else{
            User.create(username,password);
            User.authenticate(username,password);
            session("username", username);
            return ok(toJson("Success"));
        }
    }

}
