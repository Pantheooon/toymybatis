package dataSource;

import configuration.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/3.
 */
public class DataSourceTest {
    private static String sql = "insert into student (name) values (?)";

    private static PooledDataSource pooledDataSource;
    @BeforeClass
    public static void beforeClass(){
        Configuration configuration = new Configuration();
        configuration.setDriver("com.mysql.jdbc.Driver");
        configuration.setPassword("root");
        configuration.setUrl("jdbc:mysql://localhost:3306/pmj_test?autoReconnect=true&useSSL=false");
        configuration.setUserName("root");
        pooledDataSource = new PooledDataSource(configuration);
    }

    //关闭后是否推入空闲连接中
    @Test
    public void testGetConnection() throws SQLException {

       JdbcTransaction jdbcTransaction = new JdbcTransaction(pooledDataSource, false);
        Connection connection = jdbcTransaction.getConnection();
        connection.close();
        Assert.assertTrue(pooledDataSource.idelConnection.size() == 1);
    }

    //事务是否起作用
    @Test
    public void testTransaction() throws SQLException {
       JdbcTransaction jdbcTransaction = new JdbcTransaction(pooledDataSource, false);
        Connection connection = jdbcTransaction.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "pmj");
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        while (rs.next()) {
            int anInt = rs.getInt(1);
            System.out.println(anInt);
        }
        jdbcTransaction.rollback();
        connection.close();
    }

}
