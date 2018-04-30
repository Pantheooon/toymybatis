package configuration;

import binding.MapperProxy;
import binding.MapperProxyFactory;
import dataSource.PooledDataSource;
import dataSource.TransactionFactory;
import exception.HasParsedException;
import mapping.MappedStatement;
import session.SqlSession;
import sql.Sql;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/8.
 */
public class Configuration {

    private String userName;
    private String url;
    private String password;
    private String driver;
    private Map<String, MappedStatement> statements = new HashMap();
    private Map<Class, MapperProxyFactory> mapperRegistry = new HashMap<>();
    private DataSource dataSource;
    private TransactionFactory transactionFactory;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {

        this.driver = driver;
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory mapperProxyFactory = mapperRegistry.get(type);
        return (T) mapperRegistry.get(type).newInstanceProxy(sqlSession);
    }

    public <T> void addMapper(Class<T> type) {
        boolean containsKey = mapperRegistry.containsKey(type);
        if (containsKey) {
            throw new HasParsedException("the class" + type.getName() + " has already loaded into maps");
        }
        mapperRegistry.put(type, new MapperProxyFactory(type));
    }

    public void addStatement(String key, MappedStatement mappedStatement) {
        if (statements.containsKey(key)) {
            throw new HasParsedException("the statements " + key + "has been parsed");
        }
        statements.put(key, mappedStatement);
    }

    public MappedStatement getMappedStatement(String key) {
        if (!statements.containsKey(key)) {
            return null;
        }
        return statements.get(key);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(PooledDataSource dataSource) {
        this.dataSource = dataSource;
        this.transactionFactory = new TransactionFactory(dataSource);
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }
}
