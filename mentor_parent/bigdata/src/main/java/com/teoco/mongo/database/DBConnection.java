package com.teoco.mongo.database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.sql.CommonDataSource;
import javax.sql.ConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by roysha on 2/17/2016.
 */
public class DBConnection {
    private static  DB db;
    private static Mongo mongo;
    private static MongoClient client;
    private static MongoDatabase mongoDatabase;
    private static DBConnection instance=null;
    private static Connection connection=null;
    private DBConnection(){

    }

    public static DBConnection getInstance(){
        if(instance==null)
            instance=new DBConnection();
        return  instance;
    }
   private  DB getConnectionDetails(){
        mongo = new Mongo("localhost", 27011);
        db = mongo.getDB("ran");
      return  db;
   }

    private MongoDatabase getConnectionDetailsFromClient(){
         client=new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27011)));
               // new ServerAddress("localhost", 27013)));
         mongoDatabase= client.getDatabase("ran");
        return mongoDatabase;

    }
    public DBCollection getCollectionName(String collectionName){
        DBCollection collection=null;
            if(db!=null) {
                collection = (DBCollection) db.getCollection(collectionName);
            }
            return  collection;

    }
    public MongoCollection getCollectionNameFromMongoClient(String collectionName){
        MongoCollection collection=null;
        if(mongoDatabase!=null) {
            collection= mongoDatabase.getCollection(collectionName);
        }
        return  collection;

    }

    public <Obj extends Object> Object getConnection(String name){
       if("MongoClient".equals(name)){
           return getConnectionDetailsFromClient();
       }
            return getConnectionDetails();

    }

    public void closeConnectionForDataBase(String name){
        if("MongoClient".equals(name)){
             closeConnection();
        }
        else {
            closeConnectionForMongoClient();
        }

    }

    private  void closeConnection(){
        mongo.close();
    }
    private  void closeConnectionForMongoClient(){
        client.close();
    }

    public static Connection getConnectionForPhoenix() throws ClassNotFoundException {
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        try {
            Connection connection = DriverManager.getConnection("jdbc:phoenix:10.80.130.108:2181/hbase");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }
    public static void closeConnectionForPhoenix()  {
        try {
            if(connection!=null)
                connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
