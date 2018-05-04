package controllers;

import io.ebean.Query;
import play.data.FormFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login.*;

import models.User;

import javax.inject.Inject;
import java.util.Map;

public class LoginController extends Controller {

    private static FormFactory formFactory;

    @Inject
    public LoginController(FormFactory formFactory) {
        this.formFactory=formFactory;
    }

    public Result index() {
        if (session("username")==null) {
            Form<User> userForm = formFactory.form(User.class);
            return ok(index.render(userForm));
        } else {
            return redirect(routes.HomeController.index());
        }
    }

    public Result register(){
        if (session("username")==null) {
            Form<User> userForm = formFactory.form(User.class);
            return ok(register.render(userForm));
        } else {
            return redirect(routes.HomeController.index());
        }
    }

    public Result authenticate(){
            Form<User> userForm = formFactory.form(User.class).bindFromRequest();
            Map<String, String> data=userForm.rawData();
            String candidateName=data.get("username");
            String candidatepw=data.get("password");
            if (!userForm.hasErrors()) {
                Query<User> query=User.find.nativeSql("SELECT * FROM users WHERE username = '"+candidateName+"' LIMIT 1;");
                User compare = query.findOne();
                if (compare != null) {
                    if (compare.checkpw(candidatepw)){
                        session("username",candidateName);
                    } else {
                        flash("login_error", "Incorrect password");
                        return redirect(routes.LoginController.index());
                    }
                } else {
                    flash("login_error","User does not exist");
                    return redirect(routes.LoginController.index());
                }
            } else {
                return badRequest(index.render(userForm));
            }
        return redirect(routes.HomeController.index());
    }

    public Result createUser() {
        try {
            Form<User> userForm = formFactory.form(User.class).bindFromRequest();
            if (!userForm.hasErrors()) {
                User user = userForm.get();
                if (User.find.byId(user.username)!=null){
                    return badRequest(register.render(userForm));
                } else {
                    user.save();
                    //System.out.println(user.getPassword());
                    session("username", user.username);
                }
            } else {
                return badRequest(register.render(userForm));
            }
        } catch (Exception e) {}
        return redirect(routes.HomeController.index());
    }

    public Result logout(){
        session().remove("username");
        return redirect(routes.LoginController.index());
    }
}
