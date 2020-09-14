package models;

import java.util.HashSet;
import java.util.Set;

import io.ebean.Model;
import javax.persistence.Entity;

@Entity
public class Result extends Model{
    private String paperTitle;
    private int year;
    private Set<String> coAuthors = new HashSet<String>();
    private String publicationChannel;
    private String pages;
    private String id;

    public Result(String id) {
        this.id = id;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<String> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(Set<String> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public String getPublicationChannel() {
        return publicationChannel;
    }

    public void setPublicationChannel(String publicationChannel) {
        this.publicationChannel = publicationChannel;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
