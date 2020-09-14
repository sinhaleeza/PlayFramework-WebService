package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Model;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.concurrent.CompletionStage;

@Entity
public class AuthorPublications extends Model {

    public String author;
    public String year;
    @Id
    public String id;

    public AuthorPublications() {
    }

    public AuthorPublications(String author, String year, String id) {
        this.author = author;
        this.year = year;
        this.id = id;
    }

    /**
     * Entity bean used for Query7
     * @return
     */
    public CompletionStage<WSResponse> listAuthorPublications(){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/mashUp1");
        ObjectNode res = Json.newObject();
        System.out.println(this.toString());
        res.put("author",this.author);
        res.put("year", this.year);
        return req.addHeader("Content-Type","application/json")
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    @Override
    public String toString() {
        return "AuthorArticles{" +
                "author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
