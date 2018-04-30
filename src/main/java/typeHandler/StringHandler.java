package typeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/26.
 */
public class StringHandler extends TypeHandler {
    @Override
    public void setParam(int i, PreparedStatement ps, Object value) throws SQLException {
        ps.setString(i, (String) value);
    }

    @Override
    public String handleResult(ResultSet resultSet) throws SQLException {
        return resultSet.getString(getName());
    }
}
