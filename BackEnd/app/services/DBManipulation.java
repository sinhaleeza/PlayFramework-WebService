package services;

import com.mysql.cj.jdbc.MysqlDataSource;
import models.*;

import java.sql.*;
import java.util.*;

public class DBManipulation {
    private MysqlDataSource dataSource = new MysqlDataSource();
    Connection connection = null;

    /**
     * DB Select/Insert Queries
     */

    public static final String QUERY1_SQL = "SELECT AUTHORS FROM ASSIGNMENT.PUBLICATION WHERE AUTHORS LIKE ?";
    public static final String QUERY2_JOURNAL_SQL =
            "SELECT ASSIGNMENT.PUBLICATION.PAPERTITLE,ASSIGNMENT.PUBLICATION.AUTHORS,ASSIGNMENT.PUBLICATION.PUBLICATIONCHANNEL,ASSIGNMENT.PUBLICATION.YEAR,ASSIGNMENT.JOURNALPUBLICATION.PAGES " +
            "FROM ASSIGNMENT.PUBLICATION, ASSIGNMENT.JOURNALPUBLICATION " +
            "WHERE ASSIGNMENT.PUBLICATION.JOURNALID = ASSIGNMENT.JOURNALPUBLICATION.PAPERID " +
            "AND ASSIGNMENT.PUBLICATION.PAPERTITLE = ?";
    public static final String QUERY2_CONFERENCE_SQL =
            "SELECT ASSIGNMENT.PUBLICATION.PAPERTITLE,ASSIGNMENT.PUBLICATION.AUTHORS,ASSIGNMENT.PUBLICATION.PUBLICATIONCHANNEL,ASSIGNMENT.CONFERENCEPUBLICATION.TIME,ASSIGNMENT.CONFERENCEPUBLICATION.PAGES " +
                    "FROM ASSIGNMENT.PUBLICATION, ASSIGNMENT.CONFERENCEPUBLICATION " +
                    "WHERE ASSIGNMENT.PUBLICATION.CONFERENCEID = ASSIGNMENT.CONFERENCEPUBLICATION.PAPERID " +
                    "AND ASSIGNMENT.PUBLICATION.PAPERTITLE = ?";

    public static final String QUERY3_SQL = "SELECT * FROM ASSIGNMENT.JOURNALPUBLICATION WHERE JOURNALNAME LIKE ? AND VOLUME=? AND NUMBER=?";
    public static final String QUERY3_JOIN_SQL =
            "SELECT ASSIGNMENT.PUBLICATION.PAPERTITLE,ASSIGNMENT.PUBLICATION.AUTHORS,ASSIGNMENT.PUBLICATION.PUBLICATIONCHANNEL,ASSIGNMENT.JOURNALPUBLICATION.TIME,ASSIGNMENT.JOURNALPUBLICATION.PAGES " +
                    "FROM ASSIGNMENT.PUBLICATION, ASSIGNMENT.JOURNALPUBLICATION " +
                    "WHERE ASSIGNMENT.PUBLICATION.JOURNALID = ASSIGNMENT.JOURNALPUBLICATION.PAPERID " +
                    "AND ASSIGNMENT.JOURNALPUBLICATION.PAPERID = ?";
    public static final String QUERY4_SQL = "SELECT * FROM ASSIGNMENT.CONFERENCE WHERE publisher LIKE ? AND (YEAR between ? and ?)";

    /**
     * Connect to the MYSQL DB
     */
    public void connectDB(){
        try {
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("assignment");
        connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect from the MYSQL DB
     */
    public void disconnectDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert results of DB Query 1
     * @param author - author
     */
    public String queryResult1(String author) {
        Set<String> coAuthors = new HashSet<String>();
        try {
            if (null != connection) {
                PreparedStatement stmt = connection.prepareStatement(QUERY1_SQL);
                stmt.setString(1, "%"+author+"%");
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()){
                    String result = resultSet.getString("authors");
                    result = result.substring(1, result.length()-1);
                    String[] coAuthorsArr = result.trim().split(",");
                    for(String each : coAuthorsArr){
                        coAuthors.add(each);
                    }
                }
                System.out.println(coAuthors.toString());
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return coAuthors.toString();
    }

    /**
     * Insert results of DB Query 2
     * @param paperName - title
     */
    public Result2 queryResult2(String paperName) {
        Result2 result2 = new Result2();
        boolean matchFound = false;
        try {
            if (null != connection) {
                PreparedStatement stmt = connection.prepareStatement(QUERY2_JOURNAL_SQL);
                stmt.setString(1, paperName);
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()) {
                    System.out.println("Its a journal");
                    result2 = new Result2(paperName,
                            resultSet.getString("paperTitle"),
                            resultSet.getString("authors"),
                            resultSet.getString("publicationChannel"),
                            resultSet.getString("year"),
                            resultSet.getString("pages"),
                            Integer.toString(Math.abs(new Random().nextInt())));
                    matchFound = true;
                }
                if(!matchFound) {
                    stmt = connection.prepareStatement(QUERY2_CONFERENCE_SQL);
                    stmt.setString(1, paperName);
                    resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        System.out.println("Its a conference paper");
                        result2 = new Result2(paperName,
                                resultSet.getString("paperTitle"),
                                resultSet.getString("authors"),
                                resultSet.getString("publicationChannel"),
                                resultSet.getString("time"),
                                resultSet.getString("pages"),
                                Integer.toString(Math.abs(new Random().nextInt())));
                    }
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result2;
    }

    /**
     * Insert results of DB Query 3
     * @param journalName - journal
     * @param volume - volume
     * @param number - number
     */
    public Result3 queryResult3(String journalName, int volume, int number) {
        Result3 result3 = new Result3();
        System.out.println("input values : "+journalName+" : "+volume+" : "+number);
        try {
            if (null != connection) {
                PreparedStatement stmt = connection.prepareStatement(QUERY3_SQL);
                stmt.setString(1, "%"+journalName+"%");
                stmt.setInt(2, volume);
                stmt.setInt(3, number);
                ResultSet resultSet = stmt.executeQuery();
                while(resultSet.next()) {
                    System.out.println("We have some journal results");
                    PreparedStatement stmt1 = connection.prepareStatement(QUERY3_JOIN_SQL);
                    stmt1.setString(1, resultSet.getString("paperId"));
                    ResultSet resultSet1 = stmt1.executeQuery();
                    while(resultSet1.next()){
                        result3 = new Result3(journalName,volume,number,
                                resultSet1.getString("paperTitle"),
                                resultSet1.getString("authors"),
                                resultSet1.getString("publicationChannel"),
                                resultSet.getString("time"),
                                resultSet.getString("pages"),
                                Integer.toString(Math.abs(new Random().nextInt())));
                    }
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result3;
    }

    /**
     * Insert results of DB Query 4
     * @param conferenceName - conference
     */
    public Result9 queryResult9(String conferenceName, int yearStart, int yearEnd) {
        Result9 result9 = new Result9();
        try {
            if (null != connection) {
                PreparedStatement stmt = connection.prepareStatement(QUERY4_SQL);
                stmt.setString(1, conferenceName);
                stmt.setInt(2, yearStart);
                stmt.setInt(3, yearEnd);
                ResultSet resultSet = stmt.executeQuery();
                Set<String> conferenceNames = new HashSet<String>();
                while(resultSet.next()) {
                    conferenceNames.add(resultSet.getString("conferenceName"));

                }
                result9 = new Result9(conferenceNames,
                        Integer.toString(Math.abs(new Random().nextInt())));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result9;
    }

}
