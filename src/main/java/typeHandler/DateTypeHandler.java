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
        //似乎有点问题
        return new Date(resultSet.getTimestamp(getName()).getTime());
    }
}
