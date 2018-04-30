package dataSource;

import configuration.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Created by Administrator on 2018/4/3.
 */
public class ProxyConnectionImpl implements InvocationHandler {


    private Configuration configuration;
    private final String CLOSE = "close";
    private PooledDataSource pooledDataSource;
    private Connection realConnection;
    private  Connection proxyConnection;

    public ProxyConnectionImpl(PooledDataSource dataSource, Configuration configuration, Connection realConnection) {
        this.pooledDataSource = dataSource;
        this.configuration = configuration;
        this.realConnection = realConnection;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (CLOSE.equals(methodName)) {
            pooledDataSource.pushConnection(this);
            return null;
        } else {
            return method.invoke(realConnection, args);
        }
    }

    public Connection getProxyConnection() {
        return this.proxyConnection;
    }

    public Connection getRealConnection(){return this.realConnection;}
}
