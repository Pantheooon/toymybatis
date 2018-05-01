package demo;

import org.junit.Assert;
import org.junit.BeforeClass;
import session.SqlSession;
import session.SqlSessionFactory;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018/4/27.
 */
public class Test {

    static SqlSession sqlSession;

    @BeforeClass
    public static void init() {
        Properties properties = new Properties();
        properties.put("username", "root");
        properties.put("password", "root");
        properties.put("url", "jdbc:mysql://localhost:3306/pmj_test?autoReconnect=true&useSSL=false");
        properties.put("driver", "com.mysql.jdbc.Driver");
        sqlSession = new SqlSessionFactory(properties).openSqlSession(false);
    }


    @org.junit.Test
    public void test1() {

        IStudentDao mapper = sqlSession.getMapper(IStudentDao.class);
        List<Student> byId = mapper.findBy(13);
        Student student = mapper.findById(13L);
        Assert.assertEquals(byId.size(), 1);
        Assert.assertEquals(student.getName(), "pmj1");
    }


    @org.junit.Test
    public void test2() {
        IStudentDao mapper = sqlSession.getMapper(IStudentDao.class);
        mapper.updateById(17);
        Student student = mapper.findById(17L);
        Assert.assertEquals(student.getName(), "pmjj");
    }

    @org.junit.Test
    public void test3() {
        IStudentDao mapper = sqlSession.getMapper(IStudentDao.class);
        mapper.updateByName("pmj");
        Student student = mapper.findById(17L);
        Student byId = mapper.findById(20L);
        Assert.assertEquals(student.getName(), "pmj");
    }

    @org.junit.Test
    public void test4() {
        IStudentDao mapper = sqlSession.getMapper(IStudentDao.class);
        Student student = new Student();
        student.setName("pmm");
        student.setDate(new Date());
        mapper.insert(student);
        System.out.println(student);
    }

    //does transaction works?
    @org.junit.Test
    public void test5() {
        IStudentDao mapper = sqlSession.getMapper(IStudentDao.class);
        Student student = new Student();
        student.setName("pmj12");
        student.setDate(new Date());
        mapper.insert(student);
        sqlSession.commit();
        sqlSession.close();
    }
}
