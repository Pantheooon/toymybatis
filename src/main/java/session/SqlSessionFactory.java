package session;

import configuration.Configuration;
import dataSource.PooledDataSource;
import parse.BaseParse;
import parse.Parse;

import java.util.Properties;


public class SqlSessionFactory {
    private Configuration configuration;

    private Boolean parsed = false;

    private Properties properties;

    public SqlSessionFactory(Properties property) {
        this.properties = property;
        parse();

    }

    public void parse() {
        if (parsed) {
            throw new IllegalStateException("the property has been parsed....");
        }
        parsed = true;
        this.configuration = new Configuration();
        Parse mapperParse = new BaseParse(configuration, properties);
        mapperParse.parse();
        PooledDataSource dataSource = new PooledDataSource(configuration);
        configuration.setDataSource(dataSource);
    }

    public DefaultSqlSession openSqlSession() {
        return openSqlSession(true);
    }

    public DefaultSqlSession openSqlSession(Boolean auto) {
        return new DefaultSqlSession(configuration, auto);
    }
}
