package controllers;

import models.Book;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.books.*;

import javax.inject.Inject;
import java.util.List;


public class BooksController extends Controller {

    private FormFactory formFactory;

    private final Integer PAGE_SIZE=3;

    @Inject
    public BooksController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    
    // Show all books
    public Result index(Integer page) {
        if (page<=0)
            return redirect(routes.BooksController.index(1));
        List<Book> books = Book.find.all();
        Integer pagecount=(int)Math.ceil((double)books.size()/(double)PAGE_SIZE);
        if (page>pagecount)
            return redirect(routes.BooksController.index(pagecount));
        //List<Book> booksSubset = books.subList(0,0);
        List<Book> booksSubset=books.subList((page-1)*PAGE_SIZE,(page*PAGE_SIZE<=books.size()?page*PAGE_SIZE:books.size()));
        return ok(index.render(booksSubset,page,pagecount));

    }

    //just grab the inner html, for ajax requests
    public Result booklist(Integer page){
        if (page<=0)
            return redirect(routes.BooksController.index(1));
        List<Book> books = Book.find.all();
        Integer pagecount=(int)Math.ceil((double)books.size()/(double)PAGE_SIZE);
        if (page>pagecount)
            return redirect(routes.BooksController.index(pagecount));
        //List<Book> booksSubset = books.subList(0,0);
        List<Book> booksSubset=books.subList((page-1)*PAGE_SIZE,(page*PAGE_SIZE<=books.size()?page*PAGE_SIZE:books.size()));
        return ok(booklist.render(booksSubset,page,pagecount));
    }

    // Open edit page for a book
    public Result edit(Integer id) {
        if (session("username")!=null) {
            Book book = Book.find.byId(id);
            if (book == null) {
                return notFound(views.html.errorpage.render(404,"Not Found"));
            }
            Form<Book> bookForm = formFactory.form(Book.class).fill(book);
            return ok(update.render(bookForm, id));
        } else {
            return unauthorized(views.html.errorpage.render(401,"Unauthorized"));
        }
    }

    // Show specific book by ID
    public Result show(Integer id) {
        Book mybook = Book.find.byId(id);
        if (mybook != null)
            return ok(book.render(mybook));
        else
            return notFound(views.html.errorpage.render(404,"Not Found"));
    }

    // Open create page
    public Result create() {
        if (session("username")!=null) {
            Form<Book> bookForm = formFactory.form(Book.class);
            return ok(create.render(bookForm));
        } else {
            return unauthorized(views.html.errorpage.render(401,"Unauthorized"));
        }

    }

    // Save book
    public Result save() {
        try {
            Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
            if (!bookForm.hasErrors()) {
                Book book = bookForm.get();
                book.save();
                flash("message", "Successfully added " + book.title);
                flash("message_type", "success");
            } else {
                flash("message", "Form contains errors.");
                flash("message_type", "danger");
                return badRequest(create.render(bookForm));
            }
        } catch (Exception e) {
            flash("message", "Creation failed: " + e.toString());
            flash("message_type", "danger");
        }
        return redirect(routes.BooksController.index(1));
    }

    // Update book
    public Result update(Integer id) {
        try {
            Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
            if (!bookForm.hasErrors()) {
                Book book = bookForm.get();
                book.id = id;
                book.update();
                flash("message", "Successfully updated " + book.title);
                flash("message_type", "success");
            } else {
                flash("message", "Form contains errors.");
                flash("message_type", "danger");
                return badRequest(update.render(bookForm, id));
            }
        } catch (Exception e) {
            flash("message", "Update failed: " + e.toString());
            flash("message_type", "danger");
        }
        return redirect(routes.BooksController.index(1));
    }

    // Delete book
    public Result delete(Integer id) {
        try {
            Book toRemove = Book.find.byId(id);
            if (toRemove == null) {
                flash("message", "Deletion failed: Book does not exist.");
                flash("message_type", "danger");

            } else {
                toRemove.delete();
                flash("message", "Book successfully deleted.");
                flash("message_type", "success");

            }
        } catch (Exception e) {
            flash("message", "Deletion failed: " + e.toString());
            flash("message_type", "danger");

        } finally {
            return redirect(routes.BooksController.index(1));
        }
    }


}
