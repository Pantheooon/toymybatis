package typeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/25.
 */
public class IntegerHandler extends TypeHandler {
    @Override
    public void setParam(int i, PreparedStatement ps, Object value) throws SQLException {
        ps.setInt(i, (Integer) value);
    }

    @Override
    public Integer handleResult(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(getName());
    }

}
