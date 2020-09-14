package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import play.libs.concurrent.HttpExecutionContext;
import play.data.Form;
import play.data.FormFactory;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

    import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class SOCQueryController extends Controller {

    @Inject
    HttpExecutionContext ec;

    private FormFactory formFactory;

    @Inject
    public SOCQueryController(FormFactory formFactory){
        this.formFactory = formFactory;
    }


    /**
     * Get renderer for all scala html pages of the application
     * @return
     */
    public Result index(){return ok(views.html.query1.render(new ArrayList<>()));}
    public Result query1(){return ok(views.html.query1.render(new ArrayList<>()));}
    public Result query2(){return ok(views.html.query2.render(new ArrayList<>()));}
    public Result query3(){return ok(views.html.query3.render(new ArrayList<String>()));}
    public Result query4(){return ok(views.html.query4.render(new ArrayList<String>()));}
    public Result query5(){return ok(views.html.query5.render(new ArrayList<String>()));}
    public Result query6(){return ok(views.html.query6.render(new ArrayList<String>()));}
    public Result query7(){return ok(views.html.query7.render(new ArrayList<String>()));}
    public Result query8(){return ok(views.html.query8.render(new ArrayList<String>()));}
    public Result query9(){return ok(views.html.query9.render(new ArrayList<String>()));}

    /**
     *1.1 Given author name A, list all of her co-authors.
     * @param author
     * @return
     */
    public CompletionStage<Result> query1Handler(String author){
        Form<Authors> query1Form = formFactory.form(Authors.class).bindFromRequest();
        if(query1Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query1.render(new ArrayList<>()));
        }

        return query1Form.get().listCoAuthors(author)
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        System.out.println("CoAuthors for :"+author +" are : "+ws.asJson());
                        String parsedAuthors = ws.asJson().get("coAuthors").toString().replace("[\"","").replace("\"]","").replace(",\"",";").replace("\"","");
                        parsedAuthors = parsedAuthors.replace("[","").replace("]","");
                        String[] authors = parsedAuthors.split(",");
                        List<String> authorsList = new ArrayList<String>();
                        for(int i = 0 ; i < authors.length ; i++){
                            authorsList.add(authors[i]);
                        }
                        return ok(views.html.query1.render(authorsList));
                    }else{
                        return badRequest(views.html.query1.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 1.2 Given a paper name (i.e., title), list its publication metadata.
     * @param paperName
     * @return
     */
    public CompletionStage<Result> query2Handler(String paperName){
        Form<PaperMetaData> query2Form = formFactory.form(PaperMetaData.class).bindFromRequest();
        if(query2Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query2.render(new ArrayList<String>()));
        }

        return query2Form.get().listPublicationMetaData(paperName)
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        System.out.println(ws.asJson());
                        String parsedMetaData = ws.asJson().get("result2").toString().replace(",\"",";").replace("\"","");
                        parsedMetaData = parsedMetaData.replace("{","").replace("}","");
                        String[] papers = parsedMetaData.split(";");
                        List<String> papersList = new ArrayList<String>();
                        for(int i = 0 ; i < papers.length ; i++){
                            papersList.add(papers[i]);
                        }
                        return ok(views.html.query2.render(papersList));
                    }else{
                        return badRequest(views.html.query2.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 1.3 Given a journal name and a year (volume) and an issue (number), find out the metadata of all the papers published in the book (means the volume+number of the journal).
     * @return
     */
    public CompletionStage<Result> query3Handler(){
        Form<JournalMetaData> query3Form = formFactory.form(JournalMetaData.class).bindFromRequest();
        if(query3Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query3.render(new ArrayList<String>()));
        }

        return query3Form.get().listJournalMetaData()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        System.out.println(ws.asJson());
                        String parsedMetaData = ws.asJson().get("result3").toString().replace(",\"",";").replace("\"","");
                        parsedMetaData = parsedMetaData.replace("{","").replace("}","");
                        String[] journals = parsedMetaData.split(";");
                        List<String> journalsList = new ArrayList<String>();
                        for(int i = 0 ; i < journals.length ; i++){
                            journalsList.add(journals[i]);
                        }
                        return ok(views.html.query3.render(journalsList));
                    }else{
                        return badRequest(views.html.query3.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 1.4 Display all the article titles published in the area of SOC
     * @return
     */
    public CompletionStage<Result> query4Handler(){
        Form<ArticleTitles> query4Form = formFactory.form(ArticleTitles.class).bindFromRequest();
        if(query4Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query4.render(new ArrayList<String>()));
        }

        return query4Form.get().listArticleTitles()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        System.out.println(ws.asJson());
                        String parsedArticles = ws.asJson().get("result4").get("titles").toString().replace("[\"","").replace("\"]","").replace(",\"",";").replace("\"","");
                        parsedArticles = parsedArticles.replace("[","").replace("]","");
                        String[] articles = parsedArticles.split(";");
                        List<String> articlesList = new ArrayList<String>();
                        for(int i = 0 ; i < articles.length ; i++){
                            articlesList.add(articles[i]);
                        }
                        return ok(views.html.query4.render(articlesList));
                    }else{
                        return badRequest(views.html.query4.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 1.5 Display the titles of the articles published by a researcher in a specific year
     * @return
     */
    public CompletionStage<Result> query5Handler(){
        Form<AuthorArticles> query5Form = formFactory.form(AuthorArticles.class).bindFromRequest();
        if(query5Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query5.render(new ArrayList<String>()));
        }

        return query5Form.get().listAuthorArticles()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        System.out.println(ws.asJson());
                        String parsedArticles = ws.asJson().get("result5").get("titles").toString().replace("{\"","").replace("\"}","").replace(",\"",";").replace("\"","");
                        //parsedArticles = parsedArticles.replace("[","").replace("]","");
                        String[] articles = parsedArticles.split(";");
                        List<String> articlesList = new ArrayList<String>();
                        for(int i = 0 ; i < articles.length ; i++){
                            articlesList.add(articles[i]);
                        }
                        return ok(views.html.query5.render(articlesList));
                    }else{
                        return badRequest(views.html.query5.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 1.6 Display all the authors who have published more than 10 papers in the area of SOC to date
     * @return
     */
    public CompletionStage<Result> query6Handler(){
        Form<PopularAuthors> query6Form = formFactory.form(PopularAuthors.class).bindFromRequest();
        if(query6Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query6.render(new ArrayList<String>()));
        }

        return query6Form.get().listPopularAuthors()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        System.out.println(ws.asJson());
                        String parsedAuthors = ws.asJson().get("result6").get("authorsList").toString().replace("[\"","").replace("\"]","").replace(",\"",";").replace("\"","");
                        //parsedArticles = parsedArticles.replace("[","").replace("]","");
                        String[] authors = parsedAuthors.split(";");
                        List<String> authorsList = new ArrayList<String>();
                        for(int i = 0 ; i < authors.length ; i++){
                            authorsList.add(authors[i]);
                        }
                        return ok(views.html.query6.render(authorsList));
                    }else{
                        return badRequest(views.html.query6.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 2.1 Given a researcher's name and a year, list all published articles' metadata
     * Mashup query
     * @return
     */
    public CompletionStage<Result> query7Handler(){
        Form<AuthorPublications> query7Form = formFactory.form(AuthorPublications.class).bindFromRequest();
        if(query7Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query7.render(new ArrayList<String>()));
        }

        return  query7Form.get().listAuthorPublications()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        JsonNode result7 = ws.asJson();
                        String[] parsedResponse = result7.get("result7").toString().replace("\\","").split(";");
                        List<String> metaDataList = new ArrayList<>();
                        for(int i = 0 ; i<parsedResponse.length; i++){
                            String parsedMetaData = parsedResponse[i].replace("{\"","").replace("\"}}","").replace("\"","").replace("result2:","");
                            System.out.println(parsedMetaData);
                            metaDataList.add(parsedMetaData);
                        }
                        return ok(views.html.query7.render(metaDataList));
                    }else{
                        return badRequest(views.html.query7.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 2.2 For the productive authors (e.g., published more than 10 papers) in the area of SOC, list all of their co-authors
     * MashupQuery
     * @return
     */
    public CompletionStage<Result> query8Handler(){
        Form<CoAuthors> query8Form = formFactory.form(CoAuthors.class).bindFromRequest();
        if(query8Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query8.render(new ArrayList<String>()));
        }

        return query8Form.get().listCoAuthors()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        JsonNode result8 = ws.asJson();
                        String parsedAuthors = result8.get("result8").toString().replace("[\"","").replace("\"]","").replace(",\"",";").replace("\"","");
                        //parsedAuthors = parsedAuthors.replace("[","").replace("]","");
                        String[] authors = parsedAuthors.split(";");
                        List<String> authorsList = new ArrayList<String>();
                        for(int i = 0 ; i < authors.length ; i++){
                            authorsList.add(authors[i]);
                        }
                        System.out.println(ws.asJson());
                        return ok(views.html.query8.render(authorsList));
                    }else{
                        return badRequest(views.html.query8.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * 2.3 Given a conference name, and a range of years (between 2003-2019), mark the locations of where the conference was held in a map.
     * MashUpQuery
     * @return
     */
    public CompletionStage<Result> query9Handler(){
        Form<ConferenceLocations> query9Form = formFactory.form(ConferenceLocations.class).bindFromRequest();
        if(query9Form.hasErrors()){
            return (CompletionStage<Result>) badRequest(views.html.query9.render(new ArrayList<String>()));
        }

        return query9Form.get().listConferenceLocations()
                .thenApplyAsync((WSResponse ws) -> {
                    if(ws.getStatus() == 200 && ws.asJson() != null){
                        JsonNode result9 = ws.asJson();
                        String parsedConferences = result9.get("result9").get("titles").toString().replace("[\"","").replace("\"]","").replace(",\"",";").replace("\"","");
                        System.out.println(parsedConferences);
                        String[] conferences = parsedConferences.split(";");
                        List<String> conferenceLocationsList = conferenceLocationsMap(conferences);

                        return ok(views.html.query9.render(conferenceLocationsList));
                    }else{
                        return badRequest(views.html.query9.render(new ArrayList<String>()));
                    }
                }, ec.current());

    }

    /**
     * Utility method for getting the locations from the conference namme
     * @param conferences
     * @return
     */
    public List<String> conferenceLocationsMap(String[] conferences){
        List<String> conferenceLocationsList = new ArrayList<>();
        List<String> months = Arrays. asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        for(int i=0 ; i< conferences.length ; i++) {
            //Ensure that index of does not throw exception
            if(conferences[i].indexOf(',') > -1) {
                String partialConferenceLocation = conferences[i].substring(conferences[i].indexOf(',')+1, conferences[i].length()).trim();
                //Remove the month names from conference name
                for(String month : months){
                    if(partialConferenceLocation.contains(month)){
                        partialConferenceLocation = partialConferenceLocation.replace(month,"");
                    }
                }
                //Remove all numbers and characters from the conference name
                partialConferenceLocation = partialConferenceLocation.replaceAll("[0-9]","").replaceAll("-","").trim();
                //Remove salient book titles
                if(partialConferenceLocation.contains("ICWS")){
                    partialConferenceLocation = partialConferenceLocation.replaceAll("ICWS","");
                }
                if(partialConferenceLocation.contains("SCC")){
                    partialConferenceLocation = partialConferenceLocation.replaceAll("SCC","");
                }
                if(partialConferenceLocation.contains("Proceedings")){
                    partialConferenceLocation = partialConferenceLocation.replaceAll("Proceedings","");
                }
                if(partialConferenceLocation.contains("International Conference Europe")){
                    partialConferenceLocation = partialConferenceLocation.replaceAll("International Conference Europe","");
                }
                //Create a temporary list for storing the address elements and separating them from empty strings
                String[] tempList = partialConferenceLocation.split(",");
                String address = "";
                for(String temp : tempList){
                    if(!temp.trim().isEmpty()){
                        if(address.isEmpty()){
                            address = address.concat(temp.trim());
                        }else{
                            address = address.concat("|").concat(temp.trim());
                        }

                    }
                }
                System.out.println(address);
                //Add the | delimited address elements into the conference locations list
                conferenceLocationsList.add(address);

            }
        }
        return conferenceLocationsList;
    }



}
