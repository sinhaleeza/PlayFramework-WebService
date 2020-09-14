package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Result4 extends Model {

    public Set<String> titles;
    @Id
    public String id;

    public Result4() {
    }

    public Result4(Set<String> titles, String id) {
        this.titles = titles;
        this.id = id;
    }
}
