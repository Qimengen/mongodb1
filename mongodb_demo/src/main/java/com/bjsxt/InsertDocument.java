package com.bjsxt;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 添加文档
 */
public class InsertDocument {
    public static void main(String[] args) {
        InsertDocument document = new InsertDocument();
        //document.insertSingleDocument();
        document.insertManyDocument();
    }
    /**
     * 添加单个文档
     */
    public void insertSingleDocument(){
        //获取集合
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "devtest");
        // {}---->Document
        Document document = new Document();
        document.append("username","list").append("userage",26).append("userdesc","Very Good").append("userlike", Arrays.asList(new String[]{"Music","Sport"}));
        collection.insertOne(document);
    }

    /**
     * 文档的批量添加
     */
    public void insertManyDocument(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        List<Document> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Document docu = new Document();
            docu.append("username","zhangsan"+i);
            docu.append("userage",20+i);
            docu.append("userdesc","OK"+i);
            docu.append("userlike",Arrays.asList(new String[]{"Music","Sport"}));
            list.add(docu);
        }
        collection.insertMany(list);
    }

}
