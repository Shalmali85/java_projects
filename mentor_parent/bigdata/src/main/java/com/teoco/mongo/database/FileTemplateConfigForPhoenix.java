package com.teoco.mongo.database;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.teoco.mongo.entity.FileTemplateConfiguredColumn;
import org.apache.phoenix.jdbc.PhoenixConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shalmali on 8/8/16.
 */
public class FileTemplateConfigForPhoenix extends FileTemplateConfig implements IFileTemplateConfig {
    private static Map<String, Map<String, FileTemplateConfiguredColumn>> fileTemplateMap = new HashMap<String, Map<String, FileTemplateConfiguredColumn>>();

    @Override
    public Map<String, Map<String, FileTemplateConfiguredColumn>> fetchFileTemplates()  {
        HashMap<String, FileTemplateConfiguredColumn> columnMapByTarget ;
        Connection conn=null;
        FileTemplateConfiguredColumn[] fileTemplateConfiguredColumn;
        try {
            conn= DBConnection.getInstance().getConnectionForPhoenix();
           String sql="SELECT FILE_TEMPLATE_ID,FILE_TYPE,COLUMN_MAP from TEST.FILE_TEMPLATE_CONFIG";
           Statement stmt=conn.createStatement();
            ResultSet resultSet=stmt.executeQuery(sql);
            while(resultSet.next()){
                columnMapByTarget=new HashMap<String, FileTemplateConfiguredColumn>();
                String file_type= resultSet.getString("FILE_TYPE");
                String column_map= resultSet.getString("COLUMN_MAP");
                Gson gson=new Gson();
                fileTemplateConfiguredColumn=gson.fromJson(column_map,FileTemplateConfiguredColumn[].class);
                for(int i=0;i< fileTemplateConfiguredColumn.length;i++) {
                    columnMapByTarget.put(fileTemplateConfiguredColumn[i].getTc(), fileTemplateConfiguredColumn[i]);
                }
                synchronized (fileTemplateMap) {
                    fileTemplateMap.put(file_type, columnMapByTarget);

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            return fileTemplateMap;
    }


   }
