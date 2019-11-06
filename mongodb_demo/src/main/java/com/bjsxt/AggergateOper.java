package com.bjsxt;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.junit.Test;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;


/**
 * 聚合操作
 */
public class AggergateOper {

    public static void main(String[] args){
        AggergateOper oper = new AggergateOper();
        //oper.selectDocumentAggregateCount();
        //oper.selectDocumentAggregateSum();
        //oper.selectDocumentAggregateGroupBySum();
        // oper.selectDocumentAggregateGroupByWhere();
        oper.selectDocumentAggregateGroupByHaving();
    }

    /**
     * 需求：查询集合中的文档数量
     Mongo Shell:db.dev.aggregate([{$group:{_id:null,count:{$sum:1}}}])
     */
    @Test
    public void selectDocumentAggregateCount(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");

        Document sum = new Document();
        sum.put("$sum",1);

        Document count = new Document();
        count.put("_id",null);
        count.put("count",sum);

        Document group = new Document();
        group.put("$group",count);

        List<Document> list = new ArrayList<>();
        list.add(group);

        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("count"));
        }
    }

    /**
     * 需求：查询集合中所有size键中的值的总和
     Mongo Shell:db.dev.aggregate([{$group:{_id:null,totalSize:{$sum:"$size"}}}])
     */
    @Test
    public void selectDocumentAggregateSum(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");
        Document sum = new Document();
        sum.put("$sum","$size");

        Document totalSize = new Document();
        totalSize.put("_id",null);
        totalSize.put("totalSize",sum);

        Document group = new Document();
        group.put("$group",totalSize);

        List<Document> list = new ArrayList<>();
        list.add(group);

        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("totalSize"));
        }

    }

    /**
     * 需求：对title进行分组，计算每组中的size的总和
     Mongo Shell:db.dev.aggregate([{$group:{_id:"$title",totalSize:{$sum:"$size"}}}])
     */
    @Test
    public void selectDocumentAggregateGroupBySum(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");
        Document sum= new Document();
        sum.put("$sum","$size");

        Document totalSize = new Document();
        totalSize.put("_id","$title");
        totalSize.put("totalSize",sum);

        Document group = new Document();
        group.put("$group",totalSize);

        List<Document> list = new ArrayList<>();
        list.add(group);

        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("totalSize"));
        }
    }

    /**
     * 需求：查询dev集合有多少文档的size大于200。
     Mongo Shell：
     db.dev.aggregate([{$match:{size:{$gt:200}}},{$group:{_id:null,totalSize:{$sum:1}}}])
     */
    @Test
    public void selectDocumentAggregateGroupByWhere(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");
        Document gt = new Document();
        gt.put("$gt",200);

        Document size = new Document();
        size.put("size",gt);

        Document match = new Document();
        match.put("$match",size);


        Document sum = new Document();
        sum.put("$sum",1);

        Document totalSize = new Document();
        totalSize.put("_id",null);
        totalSize.put("totalSize",sum);

        Document group = new Document();
        group.put("$group",totalSize);

        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(group);

        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("totalSize"));
        }
    }

    /**
     * 需求：查询dev集合，根据title分组计算出每组的size的总和，并过滤掉总和小于200的文档。
     Mongo Shell：
     db.dev.aggregate([{$group:{_id:"$title",totalSize:{$sum:"$size"}}},{$match:{totalSize:{$gt:200}}}])
     */
    @Test
    public void selectDocumentAggregateGroupByHaving(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");
        Document sum = new Document();
        sum.put("$sum","$size");

        Document totalSize = new Document();
        totalSize.put("_id","$title");
        totalSize.put("totalSize",sum);

        Document group = new Document();
        group.put("$group",totalSize);

        //{$match:{totalSize:{$gt:200}}}
        Document gt = new Document();
        gt.put("$gt",200);

        Document mtotalSize = new Document();
        mtotalSize.put("totalSize",gt);

        Document match = new Document();
        match.put("$match",mtotalSize);

        List<Document> list = new ArrayList<>();
        list.add(group);
        list.add(match);
        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("totalSize"));
        }
    }
}
