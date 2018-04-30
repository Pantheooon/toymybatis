package dataSource;

import configuration.Configuration;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/4/3.
 */
public class PooledDataSource implements DataSource {

    public Configuration configuration;
    //空闲的连接
    public volatile List<ProxyConnectionImpl> idelConnection = new ArrayList<ProxyConnectionImpl>();

    private volatile int maxConnectionNum = 10;
    private String url;
    private String username;
    private String password;
    private String driver;

    public synchronized Connection getConnection() throws SQLException {
        if (!idelConnection.isEmpty()) {
            return getConnectionFromIdel();
        }
        if (maxConnectionNum > 0) {
            maxConnectionNum--;
            ProxyConnectionImpl proxyConnection = new ProxyConnectionImpl(this, configuration, openConnection(username,password));
            return proxyConnection.getProxyConnection();
        } else {
            for (; ; ) {
                if (!idelConnection.isEmpty()) {
                    return getConnectionFromIdel();
                }
            }
        }
    }

    private Connection getConnectionFromIdel() {
        Connection connection = idelConnection.get(0).getProxyConnection();
        idelConnection.remove(0);
        return connection;
    }

    private Connection openConnection(String username,String password) {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PooledDataSource(Configuration configuration) {
        this.configuration = configuration;
        this.driver = configuration.getDriver();
        this.username = configuration.getUserName();
        this.url = configuration.getUrl();
        this.password = configuration.getPassword();
    }


    public Connection getConnection(String username, String password) throws SQLException {
        return openConnection(username,password);
    }

    public void pushConnection(ProxyConnectionImpl connection) {
        idelConnection.add(connection);
    }


    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public Logger getParentLogger() {
        return null;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
