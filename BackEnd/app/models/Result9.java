package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Result9 extends Model {

    public Set<String> titles;
    @Id
    public String id;

    public Result9() {
    }

    public Result9(Set<String> titles, String id) {
        this.titles = titles;
        this.id = id;
    }
}
