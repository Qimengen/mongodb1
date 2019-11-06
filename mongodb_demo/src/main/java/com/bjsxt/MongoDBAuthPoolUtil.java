package com.bjsxt;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * 支持用户认证的池连
 */
public class MongoDBAuthPoolUtil {
    private static MongoClient client = null;
    static{
        if(client == null){
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.connectionsPerHost(10);//每个地址的最大连接数
            builder.connectTimeout(5000);//连接超时时间
            builder.socketTimeout(5000);//设置读写操作超时时间

            MongoCredential credential = MongoCredential.createCredential("itsxt","develop","itsxtpwd".toCharArray());

            ServerAddress address = new ServerAddress("192.168.239.131",27017);
            client = new MongoClient(address,credential,builder.build());
        }
    }

    //获取MongoDB数据库
    public static MongoDatabase getDatabase(String dbName){
        return client.getDatabase(dbName);
    }

    //获取MongoDB中的集合
    public static MongoCollection getCollection(String dbName, String collName){
        MongoDatabase database = getDatabase(dbName);
        return database.getCollection(collName);
    }
    //创建集合
    public static void createCollection(String dbName,String collName){
        MongoDatabase database = getDatabase(dbName);
        database.createCollection(collName);
    }

    //删除集合
    public static void dropCollection(MongoCollection coll){
        coll.drop();
    }
}
