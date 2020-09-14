package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Result5 extends Model {

    public String titles;
    @Id
    public String id;

    public Result5() {
    }

    public Result5(String titles, String id) {
        this.titles = titles;
        this.id = id;
    }
}
