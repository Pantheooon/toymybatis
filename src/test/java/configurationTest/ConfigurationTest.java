package configurationTest;

import org.junit.Test;
import session.SqlSession;
import session.SqlSessionFactory;

import java.util.Properties;

/**
 * Created by Administrator on 2018/4/23.
 */
public class ConfigurationTest {


    @Test
    public void test() {
        Properties properties = new Properties();
        properties.put("username","pmk");
        properties.put("password","hahha");
        properties.put("url","www.baidu.com");
        properties.put("driver","adas");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory(properties);
    }

}
