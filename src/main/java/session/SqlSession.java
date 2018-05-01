package session;

import configuration.Configuration;
import mapping.MappedStatement;
import mapping.ParamMap;

import java.io.Closeable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/17.
 */
public interface SqlSession extends Closeable {


    //返回多个
    <T> List<T> select(String statement, Map<Integer, ParamMap> paramMapMap, String sql);

    //返回一个
    <T> T selectOne(String statement, Map<Integer, ParamMap> paramMapMap, String sql);


    void updateOrDelete(Map<Integer, ParamMap> paramMapMap, String sql);

    void insert(String statement, Map<Integer, ParamMap> parameters, String sql,Object param);

    void close();

    Configuration getConfiguration();

    <T> T getMapper(Class<T> tClass);

    void autoCommit(boolean autocommit);

    void rollback();

    MappedStatement getMappedStatement(Method method);

    void commit();
}
