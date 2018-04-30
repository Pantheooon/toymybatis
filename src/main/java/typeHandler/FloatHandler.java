package typeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/26.
 */
public class FloatHandler extends TypeHandler {
    @Override
    public void setParam(int i, PreparedStatement ps, Object value) throws SQLException {
        ps.setFloat(i, (Float) value);
    }

    @Override
    public Float handleResult(ResultSet resultSet) throws SQLException {
        return resultSet.getFloat(getName());
    }
}
