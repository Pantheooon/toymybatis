package resultSet;

import com.sun.org.apache.xpath.internal.operations.Bool;
import mapping.MappedStatement;
import mapping.ReturnType;
import org.apache.commons.beanutils.BeanUtils;
import typeHandler.IntegerHandler;
import typeHandler.TypeHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/30.
 */
public class DefaultResultSetHandler extends ResultSetHandler {
    public DefaultResultSetHandler(MappedStatement mappedStatement, ResultSet resultSet) throws Exception {
        super(mappedStatement, resultSet);
    }

    @Override
    public <T> List<T> handleResult() throws Exception {
        List<T> list = new ArrayList<T>();
        ReturnType returnType = mappedStatement.getReturnType();
        Class generalReturnType = returnType.getGeneralReturnType();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (columnCount == 1) {
            while (resultSet.next()) {
                T object = (T) resultSet.getObject(1);
                list.add(object);
            }

        } else {
            while (resultSet.next()) {
                T obj = (T) generalReturnType.newInstance();
                for (TypeHandler typeHandler : typeHandlers) {
                    BeanUtils.setProperty(obj, typeHandler.getName(), typeHandler.handleResult(resultSet));
                }
                list.add(obj);
            }

        }

        return list;
    }

}
