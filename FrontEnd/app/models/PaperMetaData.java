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
public class PaperMetaData extends Model {

    public String paperName;
    public String paperTitle;
    public String authors;
    public String publicationChannel;
    public String time;
    public String pages;
    @Id
    public String id;

    public PaperMetaData() {
    }

    public PaperMetaData(String paperName, String paperTitle, String authors, String publicationChannel, String time, String pages, String id) {
        this.paperName = paperName;
        this.paperTitle = paperTitle;
        this.authors = authors;
        this.publicationChannel = publicationChannel;
        this.time = time;
        this.pages = pages;
        this.id = id;
    }
    /**
     * Entity bean used for Query2
     * @param paperName
     * @return
     */
    public CompletionStage<WSResponse> listPublicationMetaData(String paperName){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/query2");
        ObjectNode res = Json.newObject();
        System.out.println(this.toString());
        if(paperName.isEmpty()) {
            res.put("paperName", this.paperName);
        }else{
            res.put("paperName", paperName);
        }
        return req.addHeader("Content-Type","application/json")
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    @Override
    public String toString() {
        return "Result2{" +
                "paperName='" + paperName + '\'' +
                ", paperTitle='" + paperTitle + '\'' +
                ", authors='" + authors + '\'' +
                ", publicationChannel='" + publicationChannel + '\'' +
                ", time='" + time + '\'' +
                ", pages='" + pages + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
