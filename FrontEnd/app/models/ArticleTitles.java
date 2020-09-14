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
public class ArticleTitles extends Model {

    public String titles;
    @Id
    public String id;

    public ArticleTitles() {
    }

    /**
     * Entity bean used for Query4
     * @return
     */
    public CompletionStage<WSResponse> listArticleTitles(){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/query4");
        System.out.println(this.toString());
        return req.addHeader("Content-Type","application/json")
                .get()
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    public ArticleTitles(String titles, String id) {
        this.titles = titles;
        this.id = id;
    }
}
