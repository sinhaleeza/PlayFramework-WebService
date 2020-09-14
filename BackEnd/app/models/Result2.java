package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Result2 extends Model {

    public String paperName;
    public String paperTitle;
    public String authors;
    public String publicationChannel;
    public String time;
    public String pages;
    @Id
    public String id;

    public Result2() {
    }

    public Result2(String paperName, String paperTitle, String authors, String publicationChannel, String time, String pages, String id) {
        this.paperName = paperName;
        this.paperTitle = paperTitle;
        this.authors = authors;
        this.publicationChannel = publicationChannel;
        this.time = time;
        this.pages = pages;
        this.id = id;
    }
}
