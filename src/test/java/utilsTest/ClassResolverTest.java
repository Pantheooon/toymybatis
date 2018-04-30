package utilsTest;

import org.junit.Assert;
import org.junit.Test;
import parse.DefaultSqlHandler;
import parse.SqlHandler;
import sql.annotation.Mapper;
import util.ClassesResolver;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/20.
 */
public class ClassResolverTest {

    @Test
    public void test() throws Exception {
        ClassesResolver classesResolver = new ClassesResolver();
        Set<Class> test = classesResolver.resoveClasses();
        Assert.assertEquals(test.size(), 1);
    }

    @Test
    public void test4() {
        SqlHandler sqlHandler = new DefaultSqlHandler("#{", "}");
        List<String> strings = sqlHandler.parseSql("select id as id,class_no as classNo from classes");
        System.out.println(strings);
    }
}
