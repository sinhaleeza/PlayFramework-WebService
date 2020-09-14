package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Model;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import java.util.concurrent.CompletionStage;

@Entity
public class JournalMetaData extends Model {

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

    public JournalMetaData() {
    }

    public JournalMetaData(String journalName, int volume, int number, String paperTitle, String authors, String publicationChannel, String time, String pages, String id) {
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

    /**
     * Entity bean used for Query3
     * @return
     */
    public CompletionStage<WSResponse> listJournalMetaData(){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/query3");
        ObjectNode res = Json.newObject();
        System.out.println(this.toString());
        res.put("journalName",this.journalName);
        res.put("volume", this.volume);
        res.put("number", this.number);
        return req.addHeader("Content-Type","application/json")
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    @Override
    public String toString() {
        return "JournalMetaData{" +
                "journalName='" + journalName + '\'' +
                ", volume=" + volume +
                ", number=" + number +
                ", paperTitle='" + paperTitle + '\'' +
                ", authors='" + authors + '\'' +
                ", publicationChannel='" + publicationChannel + '\'' +
                ", time='" + time + '\'' +
                ", pages='" + pages + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
