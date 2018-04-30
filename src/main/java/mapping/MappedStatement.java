package mapping;

import generatedKey.SelectKey;
import typeHandler.TypeHandler;

import java.lang.reflect.Method;
import java.util.List;

public class MappedStatement<T> {
    private String id;
    private SqlCommandType sqlCommandType;
    private Class relatedClass;
    private Method relatedMethod;
    private Boolean geratekey;
    private SelectKey selectKey;
    private String keyColumn;
    private ReturnType<T> returnType;
    private List<TypeHandler> resultTypeHandler;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public Class getRelatedClass() {
        return relatedClass;
    }

    public Method getRelatedMethod() {
        return relatedMethod;
    }

    public void setRelatedMethod(Method relatedMethod) {
        this.relatedMethod = relatedMethod;
    }

    public Boolean getGeratekey() {
        return geratekey;
    }

    public void setGeratekey(Boolean geratekey) {
        this.geratekey = geratekey;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }


    public void setRelatedClass(Class relatedClass) {
        this.relatedClass = relatedClass;
    }

    public ReturnType<T> getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType<T> returnType) {
        this.returnType = returnType;
    }

    public List<TypeHandler> getResultTypeHandler() {
        return resultTypeHandler;
    }

    public void setResultTypeHandler(List<TypeHandler> resultTypeHandler) {
        this.resultTypeHandler = resultTypeHandler;
    }

    public SelectKey getSelectKey() {
        return selectKey;
    }

    public void setSelectKey(SelectKey selectKey) {
        this.selectKey = selectKey;
    }
}
