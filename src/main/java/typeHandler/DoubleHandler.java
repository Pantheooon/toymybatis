package typeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/26.
 */
public class DoubleHandler extends TypeHandler {
    @Override
    public void setParam(int i, PreparedStatement ps, Object value) throws SQLException {
        ps.setDouble(i, (Double) value);
    }

    @Override
    public Double handleResult(ResultSet resultSet) throws SQLException {
        return resultSet.getDouble(getName());
    }
}
