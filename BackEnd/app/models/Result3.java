package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Result3 extends Model {

    public String journalName;
    public int volume;
    public int number;
    public String paperTitle;
    public String authors;
    public String publicationChannel;
    public String time;
    public String pages;
    @Id
    public String id;

    public Result3() {
    }

    public Result3(String journalName, int volume, int number, String paperTitle, String authors, String publicationChannel, String time, String pages, String id) {
        this.journalName = journalName;
        this.volume = volume;
        this.number = number;
        this.paperTitle = paperTitle;
        this.authors = authors;
        this.publicationChannel = publicationChannel;
        this.time = time;
        this.pages = pages;
        this.id = id;
    }
}
