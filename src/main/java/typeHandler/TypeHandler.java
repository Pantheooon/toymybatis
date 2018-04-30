package typeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/25.
 */
public abstract class TypeHandler {
    private String name;

    public abstract void setParam(int i, PreparedStatement ps, Object value) throws SQLException;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Object handleResult(ResultSet resultSet) throws SQLException;
}
