package controllers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import play.mvc.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeController extends Controller {

    public Result map() {


        List<String> conferenceLocations = new ArrayList<>();
        conferenceLocations.add("Santa Clara, USA, CA");
        conferenceLocations.add("USA, Chicago, Illinois");
        conferenceLocations.add("USA, Salt Lake City, Utah");
        return ok(views.html.map.render(conferenceLocations));
    }

}
