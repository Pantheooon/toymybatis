package typeHandler;

import java.sql.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/30.
 */
public class DateTypeHandler extends TypeHandler {
    @Override
    public void setParam(int i, PreparedStatement ps, Object value) throws SQLException {
        Date date = (Date) value;
        ps.setTimestamp(i, new Timestamp(date.getTime()));
    }

    @Override
    public Date handleResult(ResultSet resultSet) throws SQLException {

        return resultSet.getTime(getName());
    }
}
