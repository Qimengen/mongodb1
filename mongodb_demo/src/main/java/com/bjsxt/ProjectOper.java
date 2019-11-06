package com.bjsxt;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * $project 投影操作
 */
public class ProjectOper {
    public static void main(String[] args){
        ProjectOper oper = new ProjectOper();
        // oper.selectDocumentProject();
        //oper.selectDocumentProjectConcat();
        // oper.selectDocumentProjectAdd();
        oper.selectDocumentProjectDate();

    }

    /**
     * 需求：查询dev集合，将数组中的内容拆分显示，并只显示title键与tags键的值。
     Mongo Shell：
     db.dev.aggregate([{$unwind:"$tags"},{$project:{_id:0,tags:"$tags",title:"$title"}}])
     */
    @Test
    public void selectDocumentProject(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");
        Document unwind = new Document();
        unwind.put("$unwind","$tags");

        Document pro = new Document();
        pro.put("_id",0);
        pro.put("tags","$tags");
        pro.put("title","$title");

        Document project = new Document();
        project.put("$project",pro);

        List<Document> list = new ArrayList<>();
        list.add(unwind);
        list.add(project);
        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }
    }

    /**
     * 需求：查询dev集合，将数组中的内容拆分显示。将title字段和tags字段的值拼接为一个完整字符串并在Title_Tags字段中显示。
     Mongo Shell：
     db.dev.aggregate([{$unwind:"$tags"},{$project:{_id:0,Title_Tags:{$concat:["$title","-","$tags"]}}}])
     */
    public void selectDocumentProjectConcat(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");
        Document unwind = new Document();
        unwind.put("$unwind","$tags");

        Document concat = new Document();
        concat.put("$concat", Arrays.asList(new String[]{"$title","-","$tags"}));

        Document title = new Document();
        title.put("_id",0);
        title.put("Title_Tags",concat);

        Document project = new Document();
        project.put("$project",title);

        List<Document> list = new ArrayList<>();
        list.add(unwind);
        list.add(project);
        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }

    }

    /**
     * 需求：查询dev集合中数据，显示title和size字段，为size字段数据做加1操作，显示字段命名为New_Size。排除那些没有size键的文档。
     Mongo Shell：
     db.dev.aggregate([{$match:{size:{$ne:null}}},{$project:{_id:0,title:1,New_Size:{$add:["$size",1]}}}])
     */
    public void selectDocumentProjectAdd(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","dev");

        Document ne = new Document();
        ne.put("$ne",null);

        Document size = new Document();
        size.put("size",ne);

        Document match = new Document();
        match.put("$match",size);

        //{$project:{_id:0,title:1,New_Size:{$add:["$size",1]}}}
        Document add = new Document();
        add.put("$add",Arrays.asList(new Object[]{"$size",1}));

        Document new_Size = new Document();
        new_Size.put("_id",0);
        new_Size.put("title",1);
        new_Size.put("New_Size",add);

        Document project = new Document();
        project.put("$project",new_Size);

        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(project);
        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }
    }

    /**
     * 需求：查询devtest集合查询那些有生日的用户，并按照YYYY年mm月dd日 HH:MM:SS格式显示日期。
     * 注意：如果直接在MongoDB中做日期的格式化处理，那么是按照表示UTC时间来处理的，会少8个小时。建议在程序中
     * 通过java.util.Date来做日期的转换。
     Mongo Shell：
     db.devtest.aggregate([{$match:{userbirth:{$ne:null}}},{$project:{自定义日期格式:{$dateToString:{format:"%Y年%m月%d日 %H:%M:%S",date:"$userbirth"}}}}])
     */
    @Test
    public void selectDocumentProjectDate(){
        MongoCollection collection = MongoDBAuthPoolUtil.getCollection("develop","devtest");

        Document ne = new Document();
        ne.put("$ne",null);

        Document birth = new Document();
        birth.put("userbirth",ne);

        Document match = new Document();
        match.put("$match",birth);

        //{$project:{自定义日期格式:{$dateToString:{format:"%Y年%m月%d日 %H:%M:%S",date:"$userbirth"}}}}
        Document format = new Document();
        format.put("format","%Y年%m月%d日 %H:%M:%S");
        format.put("date","$userbirth");

        Document dateToString = new Document();
        dateToString.put("$dateToString",format);

        Document custoDate = new Document();
        custoDate.put("自定义日期格式",dateToString);

        Document project = new Document();
        project.put("$project",custoDate);
        List<Document> list = new ArrayList<>();
        list.add(match);
        list.add(project);
        AggregateIterable iterable =  collection.aggregate(list);
        MongoCursor<Document> cursor = iterable.iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }
    }
}
