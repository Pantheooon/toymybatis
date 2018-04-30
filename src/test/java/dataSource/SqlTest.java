package dataSource;

import org.junit.Test;
import sql.Sql;

/**
 * Created by Administrator on 2018/4/16.
 */
public class SqlTest {

    @Test
    public void testSql() {
        Sql sql = new Sql();
        String build = sql.select("a.id,p.id").from("student").join("class").where("a=#{aa}").groupBy("a.id").build();
        System.out.println(build);
    }
}
