package excutor;

import configuration.Configuration;
import dataSource.Transaction;
import mapping.MappedStatement;
import mapping.ParamMap;
import org.apache.commons.beanutils.BeanUtils;
import resultSet.DefaultResultSetHandler;
import typeHandler.TypeHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/26.
 */
public class BaseExcutor implements Excutor {

    private Configuration configuration;
    private Transaction transaction;
    private Boolean closed;
    private Boolean autoCommit;

    public BaseExcutor(Configuration configuration, Transaction transaction, Boolean autoCommit) {
        this.configuration = configuration;
        this.transaction = transaction;
        this.autoCommit = autoCommit;
        this.closed = false;
    }

    @Override
    public <T> List<T> select(MappedStatement mappedStatement, Map<Integer, ParamMap> paramMapMap, String sql) {
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(autoCommit);
            PreparedStatement ps = connection.prepareStatement(sql);
            if (paramMapMap != null) {
                for (int i = 0; i < paramMapMap.size(); i++) {
                    ParamMap paramMap = paramMapMap.get(i);
                    TypeHandler typeHandler = paramMap.getTypeHandler();
                    typeHandler.setParam(i + 1, ps, paramMap.getValue());
                }
            }

            resultSet = ps.executeQuery();
            return new DefaultResultSetHandler(mappedStatement, resultSet).handleResult();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return null;
    }

    @Override
    public void update(Map<Integer, ParamMap> paramMapMap, String sql) {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(autoCommit);
            PreparedStatement ps = connection.prepareStatement(sql);
            if (paramMapMap != null) {
                for (int i = 0; i < paramMapMap.size(); i++) {
                    ParamMap paramMap = paramMapMap.get(i);
                    TypeHandler typeHandler = paramMap.getTypeHandler();
                    typeHandler.setParam(i + 1, ps, paramMap.getValue());
                }
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(MappedStatement mappedStatement, Map<Integer, ParamMap> parameters, String sql, Object param) {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(autoCommit);
            PreparedStatement ps = connection.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    ParamMap paramMap = parameters.get(i);
                    TypeHandler typeHandler = paramMap.getTypeHandler();
                    typeHandler.setParam(i + 1, ps, paramMap.getValue());
                }
            }
            ps.execute();
            if (mappedStatement.getGeratekey()) {
                Object generatedKey = mappedStatement.getSelectKey().getGeneratedKey(ps, mappedStatement.getKeyColumn());
                BeanUtils.setProperty(param, mappedStatement.getKeyColumn(), generatedKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    protected Connection getConnection() throws SQLException {
        Connection connection = transaction.getConnection();
        Boolean auto = connection.getAutoCommit();
        if (!auto.equals(autoCommit)) {
            connection.setAutoCommit(auto);
        }
        return connection;
    }

    public void rollback(boolean rollback) throws SQLException {
        if (!closed) {
            if (rollback) {
                transaction.rollback();
            }
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
