package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity
public class Result6 extends Model {

    public Set<String> authorsList;
    @Id
    public String id;

    public Result6() {
    }

    public Result6(Set<String> authorsList, String id) {
        this.authorsList = authorsList;
        this.id = id;
    }
}
