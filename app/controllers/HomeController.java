package controllers;

import play.http.HttpEntity;
import play.mvc.Controller;
import play.mvc.Result;
import play.cache.*;
import play.twirl.api.Content;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    //@Cached(key="homePage")
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result teapotRes(){
        return teapot(views.html.errorpage.render(418,"I'm a little teapot short and stout. This is my handle; this is my spout. When I get all steamed up, hear me shout! Tip me over and pour me out."));
    }

    private Result teapot(Content content){
        return new Result(418,HttpEntity.fromContent(content,"UTF-8"));
    }

}
