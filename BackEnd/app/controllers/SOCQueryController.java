package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Result7;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.*;

public class SOCQueryController extends Controller {

    DBManipulation dbManipulation = new DBManipulation();
    XQuery xQuery = new XQuery();

    /**
     * 1.1 Given author name A, list all of her co-authors.
     * Read the db to get coAuthors
     * @return
     */
    public Result query1(){
        dbManipulation.connectDB();
        JsonNode req = request().body().asJson();

        String author = req.get("author").asText();
        ObjectNode result = Json.newObject();
        result.put("coAuthors",dbManipulation.queryResult1(author));
        dbManipulation.disconnectDB();
        return ok(result);
    }

    /**
     *Given a paper name (i.e., title), list its publication metadata.
     * Read the DB to get the paper metaData
     * @return
     */
    public Result query2(){
        dbManipulation.connectDB();
        JsonNode req = request().body().asJson();

        String paperName = req.get("paperName").asText();
        ObjectNode result = Json.newObject();
        System.out.println("Executing query2:"+paperName);
        result.putPOJO("result2",dbManipulation.queryResult2(paperName));
        dbManipulation.disconnectDB();
        return ok(result);
    }

    /**
     * 1.3 Given a journal name and a year (volume) and an issue (number), find out the metadata of all the papers published in the book (means the volume+number of the journal).
     * Read the DB to geet journal meta data
     * @return
     */
    public Result query3(){
        dbManipulation.connectDB();
        JsonNode req = request().body().asJson();

        String journalName = req.get("journalName").asText();
        int volume = req.get("volume").asInt();
        int number = req.get("number").asInt();
        ObjectNode result = Json.newObject();
        result.putPOJO("result3",dbManipulation.queryResult3(journalName,volume,number));
        dbManipulation.disconnectDB();
        return ok(result);
    }

    /**
     * 1.4 Display all the article titles published in the area of SOC
     * Execute XQuery to getch all article titles
     * @return
     */
    public Result query4(){
        ObjectNode result = Json.newObject();
        result.putPOJO("result4",xQuery.processQuery1());
        return ok(result);
    }

    /**
     * 1.5 Display the titles of the articles published by a researcher in a specific year
     * Execute xQuery to fetch the author articles
     * @return
     */
    public Result query5(){
        JsonNode req = request().body().asJson();
        String author = req.get("author").asText();
        int year = req.get("year").asInt();
        ObjectNode result = Json.newObject();
        result.putPOJO("result5",xQuery.processQuery2(author,year));
        return ok(result);
    }

    /**
     * 1.6 Display all the authors who have published more than 10 papers in the area of SOC to date
     * Execute xquery to fetch popular authors
     * @return
     */
    public Result query6(){
        ObjectNode result = Json.newObject();
        System.out.println("Executing query 6");
        result.putPOJO("result6",xQuery.processQuery3());
        return ok(result);
    }

    /**
     * 2.1 Given a researcher's name and a year, list all published articles' metadata
     * Used internally by mashup1
     * Executes xquery to fetch article metadata
     * @return
     */
    public Result query7(){
        JsonNode req = request().body().asJson();
        String author = req.get("author").asText();
        int year = req.get("year").asInt();
        ObjectNode result = Json.newObject();
        Result7 result7 = xQuery.processQuery5(author,year);
        result.putPOJO("result7",xQuery.processQuery5(author,year));
        return ok(result);
    }

    /**
     * 2.3 Given a conference name, and a range of years (between 2003-2019), mark the locations of where the conference was held in a map.
     * Read into DB to get the conference names
     * @return
     */
    public Result query9(){
        dbManipulation.connectDB();
        JsonNode req = request().body().asJson();
        String conferenceName = req.get("conferenceName").asText();
        int yearStart = req.get("yearStart").asInt();
        int yearEnd = req.get("yearEnd").asInt();
        ObjectNode result = Json.newObject();
        result.putPOJO("result9",dbManipulation.queryResult9(conferenceName,yearStart,yearEnd));
        dbManipulation.disconnectDB();
        return ok(result);
    }


}
