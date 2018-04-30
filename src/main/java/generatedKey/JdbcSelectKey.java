package generatedKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/30.
 */
public class JdbcSelectKey implements SelectKey {


    //默认认为就一个吧
    @Override
    public Object getGeneratedKey(PreparedStatement preparedStatement, String keyColumn) throws SQLException {
        ResultSet rs = preparedStatement.getGeneratedKeys();
        while (rs.next()) {
            return rs.getObject(1);
        }
        return null;
    }
}
