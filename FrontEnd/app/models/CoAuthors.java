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
public class CoAuthors extends Model {

    public Set<String> popularAuthors;
    @Id
    public String id;

    public CoAuthors() {
    }

    public CoAuthors(Set<String> popularAuthors, String id) {
        this.popularAuthors = popularAuthors;
        this.id = id;
    }

    /**
     * Entity bean used for Query8
     * @return
     */
    public CompletionStage<WSResponse> listCoAuthors(){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/mashUp2");
        System.out.println("Invoking Mashup2");
        return req.addHeader("Content-Type","application/json")
                .setRequestTimeout(Duration.ofMinutes(10))
                .get()
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    @Override
    public String toString() {
        return "PopularAuthors{" +
                "popularAuthors=" + popularAuthors +
                ", id='" + id + '\'' +
                '}';
    }
}
