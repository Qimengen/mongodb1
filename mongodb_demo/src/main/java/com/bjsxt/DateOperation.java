package com.bjsxt;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class DateOperation {

    /**
     * 插入系统当前日期
     */
    @Test
    public void insertDocumentSystemDate(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        Document docu = new Document();
        docu.put("username","wangwu");
        docu.put("userage",22);
        docu.put("userdesc","Very Good");
        docu.put("userlike", Arrays.asList(new String[]{"Music","Art"}));
        docu.put("userbirth",new Date());
        collection.insertOne(docu);
    }
    /**
     * 插入指定日期
     */
    @Test
    public void insertDocumentCustoDate(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        Date date= DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss","2019-05-01 13:32:13");
        Document docu = new Document();
        docu.put("username","zhaoliu");
        docu.put("userage",24);
        docu.put("userdesc","Very Good");
        docu.put("userlike", Arrays.asList(new String[]{"Music","Art"}));
        docu.put("userbirth",date);
        collection.insertOne(docu);
    }

    /**
     * 查询日期：查询用的生日为2019-05-01 13:32:13的用户信息  $eq
     */
    @Test
    public void selectDocumentDateUseEq(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        Date date = DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss","2019-05-01 13:32:13");
        FindIterable iterable = collection.find(Filters.eq("userbirth",date));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            String temp = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss",(Date) docu.get("userbirth"));
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike")+"\t"+temp);
        }
    }

    /**
     * 查询日期：查询用的生日大于2019-01-01 00:00:00的用户信息  $gt
     */
    @Test
    public void selectDocumentDateUseGt(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        Date date = DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss","2019-01-01 00:00:00");
        FindIterable iterable = collection.find(Filters.gt("userbirth",date));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            String temp = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss",(Date) docu.get("userbirth"));
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike")+"\t"+temp);
        }
    }
}
