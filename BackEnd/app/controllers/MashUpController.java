package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Result6;
import models.Result7;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import services.DBManipulation;
import services.XQuery;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class MashUpController extends Controller {

    DBManipulation dbManipulation = new DBManipulation();
    XQuery xQuery = new XQuery();

    /**
     * 2.2 For the productive authors (e.g., published more than 10 papers) in the area of SOC, list all of their co-authors
     * Mashup for query 7 - Combines query 7 and query2
     * @return
     */
    public Result mashUp1(){
        //Create the request for query7
        JsonNode req = request().body().asJson();
        ObjectNode result = Json.newObject();
        ObjectNode response = Json.newObject();

        System.out.println("Inside mashup1");
        ObjectNode result1 = Json.newObject();
        ObjectNode response1 = Json.newObject();
        WSClient ws1 = play.test.WSTestClient.newClient(9000);
        response1.put("author", req.get("author").asText());
        response1.put("year", req.get("year").asInt());
        //Execute query7
        WSRequest req1 = ws1.url("http://localhost:9000/query7");
        CompletableFuture<WSResponse> wsResponse1 =
                req1
                        .setRequestTimeout(Duration.ofMinutes(10))
                        .addHeader("Content-Type", "application/json")
                        .post(response1).toCompletableFuture();
        String resultString = "";
        try {
            //Parse the results of query7
        String parsedArticles = wsResponse1.get().asJson().get("result7").get("titles").toString().replace("{\"","").replace("\"}","").replace(",\"",";").replace("\"","");
        parsedArticles = parsedArticles.replace("[","").replace("]","");
        String[] articles = parsedArticles.split(";");
        List<String> articlesList = new ArrayList<String>();

        //Iterate over the parsed articles from query7
        for(int i = 0 ; i < articles.length ; i++){
            //Execute query2 to fetch meta data for every article
            WSClient ws = play.test.WSTestClient.newClient(9000);
            WSRequest request = ws.url("http://localhost:9000/query2");

            String paperName = articles[i];
            System.out.println("Query 2 for paperName : " + paperName);
            response.put("paperName", paperName);
            CompletableFuture<WSResponse> wsResponse = request.addHeader("Content-Type", "application/json")
                    .post(response).toCompletableFuture();
            //Concatenate the result from Query2
            resultString = resultString.concat(wsResponse.get().asJson().toString()).concat(";");

        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        result.putPOJO("result7", resultString);
        return ok(result);
    }

    /**
     * 2.3 Given a conference name, and a range of years (between 2003-2019), mark the locations of where the conference was held in a map.
     * Mashup for query8 - Combines query6 and query1
     * @return
     */
    public Result mashUp2(){
        //Create the request object for query1
        System.out.println("Inside mashup2");
        ObjectNode result = Json.newObject();
        ObjectNode response = Json.newObject();
        Set<String> coAuthorsSet = new HashSet<>();

        //Execute query 1 to fecth popular authors
        WSClient ws1 = play.test.WSTestClient.newClient(9000);
        WSRequest req1 = ws1.url("http://localhost:9000/query6");
              CompletableFuture<WSResponse> wsResponse1 =
                req1
                        .setRequestTimeout(Duration.ofMinutes(10))
                        .addHeader("Content-Type", "application/json")
                        .get().toCompletableFuture();
        try {
            //Parsing the authors returned in response
            String parsedAuthors = wsResponse1.get().asJson().get("result6").get("authorsList").toString().replace("[\"","").replace("\"]","").replace(",\"",";").replace("\"","");

            String[] popularAuthors = parsedAuthors.split(";");
            System.out.println(parsedAuthors.length());
            int index = 0;
            //Iterating over popular authors
            for(String author : popularAuthors) {
                index++;
                //Remove this if block if running on MAC machine
                if (index == 2) {
                    break;
                }
                //Execute query1 for coAuthors of popular authors
                WSClient ws = play.test.WSTestClient.newClient(9000);
                WSRequest request = ws.url("http://localhost:9000/query1");

                response.put("author", author);
                CompletableFuture<WSResponse> wsResponse =
                        request
                                .setRequestTimeout(Duration.ofMinutes(10))
                                .addHeader("Content-Type", "application/json")
                                .post(response).toCompletableFuture();
                //Parse the coAuthors list and add them to a set to ensure removing duplication
                String coAuthors = wsResponse.get().asJson().get("coAuthors").toString().replace("\"[","").replace("]\"","").trim();
                String[] coAuthorsList = coAuthors.split(",");
                for(int i = 0; i < coAuthorsList.length ; i++){
                    coAuthorsSet.add(coAuthorsList[i]);
                }
                System.out.println("result8 : "+coAuthors);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        result.putPOJO("result8", coAuthorsSet);
        return ok(result);
    }



}
