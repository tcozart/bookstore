package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import play.data.validation.Constraints;
import services.BCrypt;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User extends Model {
    @Id
    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(16)
    public String username;

    @NotNull
    @Constraints.Required
    private String password;

    @Inject
    public static Finder<String, User> find = new Finder<>(User.class);


    public void setPassword(String password){
        String salt=BCrypt.gensalt();
        String passwordHash=BCrypt.hashpw(password,salt);
        this.password=passwordHash;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean checkpw(String password){
        return BCrypt.checkpw(password,this.password);
    }
}
