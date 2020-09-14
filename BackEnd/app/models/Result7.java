package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Result7 extends Model {

    public Set<String> titles;
    @Id
    public String id;

    public Result7() {
    }

    public Result7(Set<String> titles, String id) {
        this.titles = titles;
        this.id = id;
    }
}
