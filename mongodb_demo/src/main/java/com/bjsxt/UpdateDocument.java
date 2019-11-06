package com.bjsxt;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

/**
 * 更新文档
 */
public class UpdateDocument {
    public static void main(String[] args) {
        UpdateDocument document = new UpdateDocument();
        //document.updateSingleDocumentSingleKey();
        //document.updateSingleDocumentManyKey();
       // document.updateManyDocumentSingleKey();
       // document.updateManyDocumentManyKey();
        document.updateDocumentArray();
    }

    /**
     * 更新单个文档单个键  updateOne
     */
    public void updateSingleDocumentSingleKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop", "devtest");
        //更新文档
        /**
         *Filters 封装了条件的一个工具类
         */
        collection.updateOne(Filters.eq("username","list"),new Document("$set",new Document("userage",28)));
    }

    /**
     * 更新单个文档多个键  updateOne
     */
    public void updateSingleDocumentManyKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        collection.updateOne(Filters.eq("username","zhangsan0"),new Document("$set",new Document("userage",18).append("userdesc","Very Good")));
    }

    /**
     * 更新多个文档单个键  updateMany
     */
    public void updateManyDocumentSingleKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        collection.updateMany(Filters.ne("username",null),new Document("$set",new Document("userdesc","Very Good1")));
    }

    /**
     * 更新多个文档多个键  updateMany
     */
    public void updateManyDocumentManyKey(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        collection.updateMany(Filters.ne("username",null),new Document("$set",new Document("userdesc","OK").append("userage",20)));
    }


    /**
     * 更新文档中的数组  updateOne
     * {$push:{}}
     */
    public void updateDocumentArray(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        collection.updateOne(Filters.eq("username","list"),new Document("$push",new Document("userlike","Art")));
    }

}
