package dataSource;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2018/4/27.
 */
public class TransactionFactory {

    private PooledDataSource dataSource;

    public TransactionFactory(PooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Transaction newTransaction(boolean autoCommit) {
        return new JdbcTransaction(dataSource, autoCommit);
    }
}
