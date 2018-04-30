package sqlTest;

import demo.IStudentDao;
import ognl.OgnlException;
import org.junit.Assert;
import org.junit.Test;
import parse.DefaultSqlHandler;
import parse.SqlHandler;
import session.SqlSession;
import session.SqlSessionFactory;
import util.ParamUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2018/4/24.
 */
public class Test2 {


    @Test
    public void test2() {
        Properties properties = new Properties();
        properties.put("username", "pmj");
        properties.put("password", "23");
        properties.put("url", "www.baidu,com");
        properties.put("driver", "wwww");
        SqlSession sqlSession = new SqlSessionFactory(properties).openSqlSession();
        IStudentDao mapper = sqlSession.getMapper(IStudentDao.class);
        //mapper.findById(1L);
    }


    @Test
    public void test3() {
        SqlHandler paramHandler = new DefaultSqlHandler("#{", "}");
        String sql = "select * from student where id= #{  s.id} and param = #{i.param}";
        Map<Integer, String> map = new HashMap<>();
        String s = paramHandler.HandleParam(sql, map);
        Assert.assertEquals(s, "select * from student where id= ? and param = ?");
        Assert.assertEquals(map.size(), 2);
    }


    @Test
    public void test4() throws OgnlException {
        ParamUtil paramUtil = new ParamUtil();
        paramUtil.getValue(1, "aa");
    }
}
