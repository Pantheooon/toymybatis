package resultSet;

import mapping.MappedStatement;
import typeHandler.*;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/30.
 */
public abstract class ResultSetHandler {

    protected List<TypeHandler> typeHandlers;
    protected MappedStatement mappedStatement;
    protected ResultSet resultSet;

    protected ResultSetHandler(MappedStatement mappedStatement, ResultSet resultSet) throws Exception {
        this.mappedStatement = mappedStatement;
        this.resultSet = resultSet;
        typeHandlers = getReturnType(mappedStatement.getReturnType().getGeneralReturnType(), resultSet);
    }

    abstract <T> List<T> handleResult() throws Exception;

    protected List<TypeHandler> getReturnType(Class generalReturnType, ResultSet resultSet) throws Exception {
        List<TypeHandler> typeHandlers = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            TypeHandler typeHandler = null;
            String columnName = metaData.getColumnName(i);
            Field declaredField = generalReturnType.getDeclaredField(columnName);
            Class<?> type = declaredField.getType();
            if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
                typeHandler = new IntegerHandler();
            } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
                typeHandler = new IntegerHandler();
            } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
                typeHandler = new DoubleHandler();
            } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
                typeHandler = new FloatHandler();
            } else if (type.isAssignableFrom(String.class)) {
                typeHandler = new StringHandler();
            } else if (type.isAssignableFrom(Date.class)) {
                typeHandler = new DateTypeHandler();
            } else {
                typeHandler = new ObjectHandler();
            }
            typeHandler.setName(columnName);
            typeHandlers.add(typeHandler);
        }
        return typeHandlers;
    }
}
