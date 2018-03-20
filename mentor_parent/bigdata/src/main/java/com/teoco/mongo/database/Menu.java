package com.teoco.mongo.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roysha on 2/17/2016.
 */
public class Menu {

    public static void saveMenu() {

        BasicDBObject[] basicDBObjects = new BasicDBObject[2];
        Map<String, Object> documentMap = new HashMap<String, Object>();
        documentMap.put("_id", 4);
        documentMap.put("menuName", "Import Export Features");

        ArrayList list = new ArrayList();
        Map<String, Object> documentMapDetail = new HashMap<String, Object>();
        documentMapDetail.put("id", 15);
        documentMapDetail.put("subMenuName", "Import");
        documentMapDetail.put("parentId", 4);
        list.add(documentMapDetail);
        documentMapDetail = new HashMap<String, Object>();
        documentMapDetail.put("id", 16);
        documentMapDetail.put("subMenuName", "GeoJson");
        documentMapDetail.put("parentId", 4);
        list.add(documentMapDetail);

        documentMap.put("submenuGroup", list);
        //documentList.add(documentMap);
        basicDBObjects[0] = new BasicDBObject();
        basicDBObjects[0].putAll(documentMap);

        documentMap = new HashMap<String, Object>();
        documentMap.put("_id", 5);
        documentMap.put("menuName", "Filter");

        list = new ArrayList();
        documentMapDetail = new HashMap<String, Object>();
        documentMapDetail.put("id", 17);
        documentMapDetail.put("subMenuName", "Filter Manager");
        documentMapDetail.put("parentId", 5);
        list.add(documentMapDetail);
        documentMapDetail = new HashMap<String, Object>();
        documentMapDetail.put("id", 18);
        documentMapDetail.put("subMenuName", "Filter Manager Group");
        documentMapDetail.put("parentId", 5);
        list.add(documentMapDetail);

        documentMap.put("submenuGroup", list);
        //documentList.add(documentMap);
        basicDBObjects[1] = new BasicDBObject();
        basicDBObjects[1].putAll(documentMap);

        DBConnection.getInstance().getCollectionName("menu").insert(basicDBObjects);
    }

    public static void fetchMenu() {
        DBCursor dbCursor = DBConnection.getInstance().getCollectionName("menu").find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            System.out.println("menuName---" + dbObject.get("menuName"));
            BasicDBList list = (BasicDBList) dbObject.get("submenuGroup");

            BasicDBObject[] lightArr = list.toArray(new BasicDBObject[0]);
            for (BasicDBObject dbObj : lightArr) {
                System.out.println("subMenuName---" + dbObj.get("Filter Manager"));

            }
        }
    }

    public static void deleteMenu() {
        DBCursor dbCursor = DBConnection.getInstance().getCollectionName("menu").find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            System.out.println("menuName---" + dbObject.get("menuName"));
            if(dbObject.get("menuName").equals("Filter")) {
                System.out.println(dbObject.get("submenuGroup"));
                BasicDBList list = (BasicDBList) dbObject.get("submenuGroup");
                System.out.println("subMenuName---" +  list.get("1"));

                 BasicDBObject bdb= (BasicDBObject) list.get("1");
                 System.out.println("subMenuName1---" +  DBConnection.getInstance().getCollectionName("menu"));


                /*BasicDBObject[] lightArr = list.toArray(new BasicDBObject[0]);
                for (BasicDBObject dbObj : lightArr) {*/



            }



        }
    }
}
