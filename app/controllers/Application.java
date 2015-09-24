package controllers;

import model.User;
import play.Routes;
import play.data.Form;
import play.mvc.*;

import views.html.*;

import static play.data.Form.*;


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
        return ok(login.render(form(Login.class)));
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
                        controllers.routes.javascript.Notes.getNote()
                )
        );
    }

}
