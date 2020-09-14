package services;

import com.saxonica.xqj.SaxonXQDataSource;
import models.*;

import javax.xml.namespace.QName;
import javax.xml.xquery.*;
import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class XQuery {


    /**
     * Executes xquery1 from inside the xqy file
     * and invokes dbManipulation for result insertion in DB
     */
    public Result4 processQuery1(){
        Result4 result4 = new Result4();
        try {
            XQResultSequence resultSequence = processQuery(new File(System.getProperty("user.dir") + "\\xquery\\xquery1.xqy"), null);
            if(null != resultSequence) {
                Set<String> titles = new HashSet<String>();
                while (resultSequence.next()) {
                    String title = resultSequence.getItemAsString(null);
                    if(title.length() > 0){
                        title = title.replace("<title>","").replace("</title>","").trim();
                        System.out.println(title);
                        titles.add(title);
                    }
                }
                result4 = new Result4(titles,Integer.toString(Math.abs(new Random().nextInt())));
                resultSequence.close();
            }
        }catch (XQException exp){
            exp.printStackTrace();
        }
        return result4;
    }

    /**
     * Executes xquery2 from inside the xqy file
     * and invokes dbManipulation for result insertion in DB
     */
    public Result5 processQuery2(String author, int year){
        Result5 result5 = new Result5();
        try{
            XQResultSequence resultSequence = processQuery(new File(System.getProperty("user.dir")+"\\xquery\\xquery2.xqy"),author,year);
            if(null != resultSequence) {
                while (resultSequence.next()) {
                    String titles = resultSequence.getItemAsString(null);
                    if(titles.length() > 0){
                        titles = titles.replace("<title>","").replace("</title>","").trim();
                        result5 = new Result5(titles,Integer.toString(Math.abs(new Random().nextInt())));
                    }
                }
                resultSequence.close();
            }
        }catch (XQException exp){
            exp.printStackTrace();
        }
        return result5;
    }

    /**
     * Executes xquery2 from inside the xqy file
     * and parses the result to ensure unique author names are returned
     * and invokes dbManipulation for result insertion in DB
     */
    public Result6 processQuery3(){
        Result6 result6 = new Result6();
        try{
            XQResultSequence resultSequence = processQuery(new File(System.getProperty("user.dir")+"\\xquery\\xquery3.xqy"),null);
            if(null != resultSequence) {
                Set<String> authorsList = new HashSet<String>();
                while (resultSequence.next()) {
                    String author = resultSequence.getItemAsString(null);
                    if(author.length() > 0){
                        author = author.replace("<author>","").replace("</author>","").trim();
                        if(author.contains(">")){
                            author = author.substring(author.indexOf(">")+1,author.length()-1);
                        }
                        authorsList.add(author);
                    }
                }
                result6 = new Result6(authorsList,Integer.toString(Math.abs(new Random().nextInt())));
                resultSequence.close();
            }
        }catch (XQException exp){
            exp.printStackTrace();
        }
        return result6;
    }

    /**
     * Executes xquery2 from inside the xqy file
     * and invokes dbManipulation for result insertion in DB
     */
    public Result7 processQuery5(String author, int year){
        Result7 result7 = new Result7();
        try{
            Set<String> titles = new HashSet<>();
            XQResultSequence resultSequence = processQuery(new File(System.getProperty("user.dir")+"\\xquery\\xquery5.xqy"),author,year);
            if(null != resultSequence) {
                while (resultSequence.next()) {
                    String title = resultSequence.getItemAsString(null);
                    if(title.length() > 0){
                        title = title.replace("<title>","").replace("</title>","").trim();
                        titles.add(title);
                    }
                }
                result7 = new Result7(titles,Integer.toString(Math.abs(new Random().nextInt())));
                resultSequence.close();
            }
        }catch (XQException exp){
            exp.printStackTrace();
        }
        return result7;
    }

    public XQResultSequence processQuery(File file, String author, int year){
        XQResultSequence resultSequence = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            XQDataSource xqDataSource = new SaxonXQDataSource();
            XQConnection connection = xqDataSource.getConnection();
            XQPreparedExpression preparedExpression = connection.prepareExpression(inputStream);
            if(author != null) {
                preparedExpression.bindString(new QName("author"), author, null);
                preparedExpression.bindInt(new QName("year"), year, null);
                System.out.println("Binding the query expression : "+author+" : "+year);
            }
            resultSequence = preparedExpression.executeQuery();
        }catch (FileNotFoundException fex){
            fex.printStackTrace();
        }catch (XQException exp){
            exp.printStackTrace();
        }
        return resultSequence;
    }
    /**
     * Common method called for the above queries for invoking the xqy file supplied
     * @param file - xqy file name
     * @param param - query parameter, if any
     * @return XQResultSequence
     */
        public XQResultSequence processQuery(File file, String param){
            XQResultSequence resultSequence = null;
            try {
                InputStream inputStream = new FileInputStream(file);
                XQDataSource xqDataSource = new SaxonXQDataSource();
                XQConnection connection = xqDataSource.getConnection();
                XQPreparedExpression preparedExpression = connection.prepareExpression(inputStream);
                if(param != null) {
                    preparedExpression.bindString(new QName("paperName"), param, null);
                }
                resultSequence = preparedExpression.executeQuery();
            }catch (FileNotFoundException fex){
                fex.printStackTrace();
            }catch (XQException exp){
                exp.printStackTrace();
            }
            return resultSequence;
        }


}
