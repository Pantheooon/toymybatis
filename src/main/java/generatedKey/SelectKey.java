package generatedKey;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/4/30.
 */
public interface SelectKey {

        Object getGeneratedKey(  PreparedStatement preparedStatement,String keyColumn) throws SQLException;

}
