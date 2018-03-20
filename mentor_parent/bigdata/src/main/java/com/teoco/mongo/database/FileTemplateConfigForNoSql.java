package com.teoco.mongo.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.util.JSON;
import com.teoco.mongo.entity.FileTemplateConfiguredColumn;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roysha on 2/23/2016.
 */
public class FileTemplateConfigForNoSql extends FileTemplateConfig implements IFileTemplateConfig{
    private static Map<String, Map<String, FileTemplateConfiguredColumn>> fileTemplateMap = new HashMap<String, Map<String, FileTemplateConfiguredColumn>>();
@Override
    public Map<String, Map<String, FileTemplateConfiguredColumn>> fetchFileTemplates() {
    if(DBConnection.getInstance().getCollectionName("file_template_config")==null){
        fetchFileTemplatesForMongoClient();
    }else {
        DBCursor dbCursor = DBConnection.getInstance().getCollectionName("file_template_config").find();
        while (dbCursor.hasNext()) {
            DBObject dbObject = dbCursor.next();
            String type = dbObject.get("file_type").toString();
            BasicDBList list = (BasicDBList) dbObject.get("column_map");

            BasicDBObject[] lightArr = list.toArray(new BasicDBObject[0]);
            HashMap<String, FileTemplateConfiguredColumn> columnMapByTarget = new HashMap<String, FileTemplateConfiguredColumn>();
            for (BasicDBObject dbObj : lightArr) {
                FileTemplateConfiguredColumn fileTemplateConfiguredColumn = new FileTemplateConfiguredColumn();
                fileTemplateConfiguredColumn.setSc(dbObj.get("sc").toString());
                fileTemplateConfiguredColumn.setTc(dbObj.get("tc").toString());
                fileTemplateConfiguredColumn.setI((int) Float.parseFloat(dbObj.get("i").toString()));
                columnMapByTarget.put(fileTemplateConfiguredColumn.getTc(), fileTemplateConfiguredColumn);

            }
            synchronized (fileTemplateMap) {
                fileTemplateMap.put(dbObject.get("file_type").toString(), columnMapByTarget);
            }
        }
    }

        return fileTemplateMap;
    }

    public Map<String, Map<String, FileTemplateConfiguredColumn>> fetchFileTemplatesForMongoClient() {
        FindIterable findIterable = DBConnection.getInstance().getCollectionNameFromMongoClient("file_template_config").find();
       MongoCursor mongoCursor=findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = (Document) mongoCursor.next();
            ArrayList<Document> lcolumnMapList= (ArrayList) document.get("column_map");

            HashMap<String, FileTemplateConfiguredColumn> columnMapByTarget = new HashMap<String, FileTemplateConfiguredColumn>();
            for (Document doc : lcolumnMapList) {
                FileTemplateConfiguredColumn fileTemplateConfiguredColumn = new FileTemplateConfiguredColumn();
                fileTemplateConfiguredColumn.setSc(doc.get("sc").toString());
                fileTemplateConfiguredColumn.setTc(doc.get("tc").toString());
                fileTemplateConfiguredColumn.setI((int) Float.parseFloat(doc.get("i").toString()));
                columnMapByTarget.put(fileTemplateConfiguredColumn.getTc(), fileTemplateConfiguredColumn);

            }
            synchronized (fileTemplateMap) {
                fileTemplateMap.put(document.get("file_type").toString(), columnMapByTarget);
            }
        }

        return fileTemplateMap;
    }





}
