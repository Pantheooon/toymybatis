package session;

import configuration.Configuration;
import excutor.BaseExcutor;
import excutor.Excutor;
import mapping.MappedStatement;
import mapping.ParamMap;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/15.
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Excutor excutor;
    private boolean autoCommit = true;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        excutor = new BaseExcutor(configuration, configuration.getTransactionFactory().newTransaction(autoCommit), autoCommit);
    }

    public DefaultSqlSession(Configuration configuration, Boolean autoCommit) {
        this.configuration = configuration;
        this.autoCommit = autoCommit;
        excutor = new BaseExcutor(configuration, configuration.getTransactionFactory().newTransaction(true), autoCommit);
    }


    @Override
    public <T> List<T> select(String statement, Map<Integer, ParamMap> paramMapMap, String sql) {
        return excutor.select(configuration.getMappedStatement(statement), paramMapMap, sql);
    }

    @Override
    public <T> T selectOne(String statement, Map<Integer, ParamMap> paramMapMap, String sql) {
        return (T) this.select(statement, paramMapMap, sql).get(0);
    }


    @Override
    public void updateOrDelete(Map<Integer, ParamMap> paramMapMap, String sql) {
        excutor.update(paramMapMap, sql);
    }

    @Override
    public void insert(String statement, Map<Integer, ParamMap> parameters, String sql,Object param) {
        excutor.insert(configuration.getMappedStatement(statement), parameters, sql, param);
    }

    @Override
    public void close() {

    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public <T> T getMapper(Class<T> tClass) {
        return configuration.getMapper(tClass, this);
    }

    @Override
    public void autoCommit(boolean autocommit) {
        this.autoCommit = autocommit;
    }

    @Override
    public void rollback(boolean roolback) {

    }

    @Override
    public MappedStatement getMappedStatement(Method method) {
        String key = method.getDeclaringClass().getName() + "." + method.getName();
        return configuration.getMappedStatement(key);
    }
}
