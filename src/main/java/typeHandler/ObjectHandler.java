package typeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/26.
 */
public class ObjectHandler extends TypeHandler {
    @Override
    public void setParam(int i, PreparedStatement ps, Object value) throws SQLException {
        ps.setObject(i,  value);
    }

    @Override
    public Object handleResult(ResultSet resultSet) throws SQLException {
        return resultSet.getObject(getName());
    }
}
