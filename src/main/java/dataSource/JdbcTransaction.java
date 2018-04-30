package dataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/15.
 */
public class JdbcTransaction implements Transaction {

    private PooledDataSource dataSource;
    private Connection connection;
    private Boolean autoCommit;

    public JdbcTransaction(PooledDataSource dataSource, Boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = dataSource.getConnection();
        }
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    public void close() throws SQLException {
        if (!connection.getAutoCommit()) {
            connection.setAutoCommit(true);
        }
        connection.close();
    }
}
