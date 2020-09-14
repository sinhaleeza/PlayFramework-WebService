package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Model;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletionStage;

@Entity
public class Authors extends Model {

    public String author;
    public Set<String> coAuthors;
    @Id
    public String id;

    public Authors() {
    }

    public Authors(String author, Set<String> coAuthors, String id) {
        this.author = author;
        this.coAuthors = coAuthors;
        this.id = id;
    }

    /**
     * Entity bean used for Query1
     * @param author
     * @return
     */
    public CompletionStage<WSResponse> listCoAuthors(String author){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/query1");
        ObjectNode res = Json.newObject();
        System.out.println(this.toString());
        if(author.isEmpty()) {
            res.put("author", this.author);
        }else{
            res.put("author", author);
        }
        return req.addHeader("Content-Type","application/json")
                .setRequestTimeout(Duration.ofMinutes(5))
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    @Override
    public String toString() {
        return "Result1{" +
                "author='" + author + '\'' +
                ", coAuthors=" + coAuthors +
                ", id='" + id + '\'' +
                '}';
    }
}
