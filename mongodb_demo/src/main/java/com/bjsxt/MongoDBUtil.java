package com.bjsxt;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {
    private static MongoClient client=null;
    static {
        if(client==null){
            client=new MongoClient("192.168.239.131",27017);
        }
    }
    //获取MongoDB数据库
    public  static MongoDatabase getDatabase(String dbName){
        return client.getDatabase(dbName);
    }

    //获取MongoDB中的集合
    public static MongoCollection getCollection(String dbName,String collName){
        MongoDatabase database=getDatabase(dbName);
        return database.getCollection(collName);
    }
}
