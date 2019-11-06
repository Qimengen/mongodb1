package com.bjsxt;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.regex.Pattern;

public class SelectDocument {
    public static void main(String[] args) {
        SelectDocument docu = new SelectDocument();
        docu.selectDocumentAll();             //查询全部文档
        //docu.selectDocumentById();            //根据id查询
        //docu.selectDocumentConditionByGt();   //根据年龄查询文档，条件是年龄大于19
        //docu.selectDocumentConditionByType(); //根据年龄查询文档，添加是年龄的值是整数value类型(number) $type根据 value 类型检索


        //docu.selectDocumentConditionByIn();   //查询用户的名字为 zhangsan1,zhangsan2	$in包含
        //docu.selectDocumentConditionByNin();  //查询用户的名字不是 zhangsan1,zhangsan2  $nin不包含
        //docu.selectDocumentConditionByRegex();  //查询用户的名字是z开头2结尾的	$regex

        //docu.selectDocumentConditionUseAnd();   //查询用户username是zhangsan1并且年龄为20岁的用户 $and
        //docu.selectDocumentConditionUseOr();    //查询用户要求username是list，或者userage是20 或者 userdesc是Very Good  $or

    }

    /**
     * 查询全部文档
     */
    @Test
    public void selectDocumentAll(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        //返回的是一个文档的迭代器
        FindIterable<Document> iterable = collection.find();
        //Cursor 游标
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();//移动游标
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 根据_id查询文档
     */
    @Test
    public void selectDocumentById(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable<Document> iterable = collection.find(Filters.eq("_id",new ObjectId("5dc121d40bacba12389f5b17")));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 根据年龄查询文档，条件是年龄大于19   $gt  大于
     */
    @Test
    public void selectDocumentConditionByGt(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.gt("userage",19));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 根据年龄查询文档，添加是年龄的值是整数value类型(number) $type根据 value 类型检索
     */
    @Test
    public void selectDocumentConditionByType(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.type("userage","number"));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 查询用户的名字为 zhangsan1,zhangsan2		$in包含
     */
    @Test
    public void selectDocumentConditionByIn(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.in("username","zhangsan1","zhangsan2"));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 查询用户的名字不是 zhangsan1,zhangsan2  $nin 不包含
     */
    @Test
    public void selectDocumentConditionByNin(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.nin("username","zhangsan1","zhangsan2"));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 查询用户的名字是z开头2结尾的	$regex
     */
    @Test
    public void selectDocumentConditionByRegex(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.regex("username", Pattern.compile("^z.*2$")));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 查询用户username是zhangsan1并且年龄为20岁的用户 $and
     */
    @Test
    public void selectDocumentConditionUseAnd(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.and(Filters.eq("username","zhangsan1"),Filters.eq("userage",21),Filters.eq("userdesc","Very Good")));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }

    /**
     * 查询用户要求username是list，或者userage是20 或者 userdesc是Very Good  $or
     */
    @Test
    public void selectDocumentConditionUseOr(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.or(Filters.eq("username","lisi"),Filters.eq("userage",20),Filters.eq("userdesc","Very Good")));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }
    /**
     * 查询文档中username为list并且年龄为20岁，或者userdesc为Very Good   $and联合$or
     */
    @Test
    public void selectDocumentConditionAndOr(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.or(Filters.and(Filters.eq("username","list"),Filters.eq("userage",20)),Filters.eq("userdesc","Very Good")));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }
    /**
     * 查询文档中username是z开头的，根据username对结果做降序排序。1升序排序， -1降序排序规则  $sort:{username,-1}
     */
    @Test
    public void selectDocumentSorting(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");
        FindIterable iterable = collection.find(Filters.regex("username",Pattern.compile("^z"))).sort(new Document("username",-1));
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            Document docu = cursor.next();
            System.out.println(docu.get("username")+"\t"+docu.get("userage")+"\t"+docu.get("userdesc")+"\t"+docu.get("userlike"));
        }
    }
}
