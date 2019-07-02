package com.libs.modle.db;

/**
 * @ User：mo
 * <>
 * @ 功能：
 * <>
 * @ 入口：
 * <>
 * @ Time：2018/8/21 0021  17:53
 */
public class a {




//
//
//    https://www.jianshu.com/p/8035eb5da7a2
//    https://github.com/LitePalFramework/LitePal
//
//    使用上确实简单
//
//    集成流程：
//
//            1、在build.gradle里添加依赖：
////数据库框架
//    compile 'org.litepal.android:core:2.0.0'
//
//            2、在src-main目录下新建assets文件夹，新建一个名为litepal.xml的xml文件，里面为：
//
//    <?xml version="1.0" encoding="utf-8"?>
//<litepal>
//<!--名称-->
//  <dbname value="test2"/>
//<!--版本号-->
//  <version value="1"/>
//  <list>
//    <!--这里是类映射，必填-->
//    <mapping class="mo.com.db.Student"></mapping>
//  </list>
//  <!-- 直接设置这个，就表示数据库存储的位置，直接打开手机存储就可以找到 -->
//   <!--这个貌似是路径，没测试过，以后有时间。。-->
//     <!-- <storage value="guolin/database"/>-->
//</litepal>
//
//            3、在manifest里的application节点里添加：
//
//    android:name="org.litepal.LitePalApplication"
//
//    如果有自己的application，则在自己的application里初始化
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // 初始化LitePal数据库
//        LitePal.initialize(this);
//    }
//
//5、权限 有时候会权限错误
//
//    在manifest里添加
//            <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
//
//    亲测不加一般不会报错，没搞懂这个是什么原理，好像是和数据库文件的存储位置有关系
//
//6、存储实体类，需继承LitePalSupport类，必须实现set get方法，也可以直接用构造器创建
//
//7、具体操作：
//
//            （1）增：实体类直接操作
//
//    Student student = new Student("1238409","小明",21);
//    //Student student = new Student();
//    //student.setNumber("1238409");
//    //student.setName("小明");
//    //student.setAge(21);
//           student.save();
//
//   （2）删：
//
//            //表对应的类、条件、条件值
//            LitePal.deleteAll(Student.class, "id > ?", "2");
//
//   （3）改：可以调用父类的updateAll方法，也可以find出来后，修改，然后save
//
//    Student student1 = new Student();
//                    student1.setAge(0);
//                    student1.setName("111111");
//                    student1.setToDefault("age");
//                    student1.updateAll("name = ?", "小明");
//
//   （4）查：
//
//    //按条件查
//    List<Student> students1 = LitePal.where("age = ?", "21").find(Student.class);
//    //查全部
//    List<Student> list = LitePal.findAll(Student.class);
//    //获取第一条数据
//    Student firstStu = DataSupport.findFirst(Student.class);
//    //获取最后一条数据
//    Student lastStu = DataSupport.findLast(Student.class);
//    //要查询某几列
//    List<Student> someLineStu = DataSupport.select("number", "name").find(Student.class);
//    //排序查询，("列名 规则") desc降序 asc升序
//    List<Student> orderStu = DataSupport.order("age desc").find(Student.class);
//    //只查前5条
//    List<Student> limitStu = DataSupport.limit(5).find(Student.class);
//    //查询结果的偏移量，跟limit搭配可以做分页使用,下面是从第3条开始，共查询5条
//    List<Student> offsetStu = DataSupport.limit(5).offset(2).find(Student.class);
//    //多种条件组合查询
//             /*List<Student> setStu = DataSupport.select("number", "name")
//                                   .where("id > ?", "3")
//                                   .order("age desc")
//                                   .limit(5)
//                                   .offset(4)
//                                   .find(Student.class);*/
//    //使用SQL语句查询
//    List<Student> students2 = new ArrayList<>();
//    Cursor cursor =
//            LitePal.findBySQL("select * from student where age = ? and id> ?", "21", "3");
//                    if (cursor != null) {
//        while (cursor.moveToNext()) {
//            Student stu = new Student();
//            stu.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            stu.setNumber(cursor.getString(cursor.getColumnIndex("number")));
//            stu.setName(cursor.getString(cursor.getColumnIndex("name")));
//            stu.setAge(cursor.getInt(cursor.getColumnIndex("age")));
//            students2.add(stu);
//        }
//        cursor.close();
//    }








}
