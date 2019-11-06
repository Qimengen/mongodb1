package com.bjsxt;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
* 直连方式
*
* */

public class MongoDBDemo {
    public static void main(String[] args) {
        /*
        //创建连接对象
        MongoClient client = new MongoClient("192.168.239.131",27017);
        //获取MongoDB数据库
        MongoDatabase database = client.getDatabase("develop");
        //获取MongoDB中的集合
        MongoCollection collection = database.getCollection("dev");
*/
        // MongoCollection collection = MongoDBUtil.getCollection("develop", "dev");
        // MongoCollection collection = MongoDBAuthUtil.getCollection("develop", "dev");
        // MongoCollection collection = MongoDBPoolUtil.getCollection("develop", "dev");
        // MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "dev");


        //创建集合
        /* MongoDatabase database=MongoDBAuthPoolUtil.getDatabase("develop");
        database.createCollection("test");*/

        MongoDBAuthPoolUtil.createCollection("develop","devtest");

        //获取集合
        /*
        MongoDatabase database=MongoDBAuthPoolUtil.getDatabase("develop");
        MongoCollection collection = database.getCollection("test");
        System.out.println(collection.getNamespace());
            */

        //删除集合
       /* MongoDatabase database=MongoDBAuthPoolUtil.getDatabase("develop");
        MongoCollection collection = database.getCollection("test");
       MongoDBAuthPoolUtil.dropCollection(collection);*/




    }
}
