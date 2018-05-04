package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book extends Model {

    @Id
    public Integer id;

    @Constraints.Required
    public String title;

    public Double price;
    public String author;

    @Inject
    public static Finder<Integer, Book> find = new Finder<Integer, Book>(Book.class);

}
