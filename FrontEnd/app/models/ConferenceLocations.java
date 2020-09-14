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
public class ConferenceLocations extends Model {

    public String conferenceName;
    public String yearStart;
    public String yearEnd;
    @Id
    public String id;

    public ConferenceLocations() {
    }

    public ConferenceLocations(String conferenceName, String yearStart, String yearEnd, String id) {
        this.conferenceName = conferenceName;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.id = id;
    }

    /**
     * Entity bean used for Query9
     * @return
     */
    public CompletionStage<WSResponse> listConferenceLocations(){
        WSClient ws = play.test.WSTestClient.newClient(9000);
        WSRequest req = ws.url("http://localhost:9000/query9");
        ObjectNode res = Json.newObject();
        System.out.println(this.toString());
        res.put("conferenceName",this.conferenceName);
        res.put("yearStart", this.yearStart);
        res.put("yearEnd", this.yearEnd);
        return req.addHeader("Content-Type","application/json")
                .post(res)
                .thenApply((WSResponse r) -> {
                    return r;
                });
    }

    @Override
    public String toString() {
        return "ConferenceLocations{" +
                "conferenceName='" + conferenceName + '\'' +
                ", yearStart='" + yearStart + '\'' +
                ", yearEnd='" + yearEnd + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
