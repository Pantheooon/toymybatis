package parse;

import com.sun.org.apache.bcel.internal.generic.MethodGen;
import configuration.Configuration;
import exception.NoAnotationException;
import generatedKey.JdbcSelectKey;
import mapping.MappedStatement;
import mapping.ReturnType;
import mapping.SqlCommandType;
import sql.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Administrator on 2018/4/19.
 */
public class StatementParse extends Parse {

    private Method method;
    private Class clases;

    public StatementParse(Configuration configuration, Method method, Class classs) {
        super(configuration);
        this.method = method;
        this.clases = classs;
    }


    @Override
    public void parse() {
        Parameter[] parameters = method.getParameters();
        Annotation[] annotations = method.getAnnotations();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int paramCount = parameterAnnotations.length;
        if (parameters.length > 1 && parameters.length != paramCount) {
            throw new NoAnotationException("the method " + method.getName() + "has multi param,but the param annotation num is not equal to the nums of param");
        }
        MappedStatement mappedStatement = new MappedStatement();
        String id = clases.getName() + "." + method.getName();
        Mapper sqlProvider = (Mapper) clases.getAnnotation(Mapper.class);
        mappedStatement.setId(id);
        Class relatedClass = sqlProvider.sqlProvider();
        mappedStatement.setRelatedClass(relatedClass);
        for (Annotation annotation : annotations) {
            if (annotation instanceof Select) {
                Select select = (Select) annotation;
                String value = select.value();
                Method method = null;
                try {
                    method = resolveRelatedMethod(value, this.method, relatedClass);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                mappedStatement.setRelatedMethod(method);
                mappedStatement.setSqlCommandType(SqlCommandType.SELECT);
            } else if (annotation instanceof Update) {
                Update update = (Update) annotation;
                String value = update.value();
                Method method = null;
                try {
                    method = resolveRelatedMethod(value, this.method, relatedClass);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                mappedStatement.setRelatedMethod(method);
                mappedStatement.setSqlCommandType(SqlCommandType.UPDATE);
            } else if (annotation instanceof Insert) {
                Insert insert = (Insert) annotation;
                boolean geratekey = insert.geratekey();
                String column = insert.keyColumn();
                String value = insert.value();
                Method method = null;
                try {
                    method = resolveRelatedMethod(value, this.method, relatedClass);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                mappedStatement.setRelatedMethod(method);
                mappedStatement.setKeyColumn(column);
                mappedStatement.setGeratekey(geratekey);
                mappedStatement.setSqlCommandType(SqlCommandType.INSERT);
                if (geratekey) mappedStatement.setSelectKey(new JdbcSelectKey());
            } else if (annotation instanceof Delete) {
                Delete delete = (Delete) annotation;
                String value = delete.value();
                Method method = null;
                try {
                    method = resolveRelatedMethod(value, this.method, relatedClass);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                mappedStatement.setRelatedMethod(method);
                mappedStatement.setSqlCommandType(SqlCommandType.DELETE);
            } else {
                throw new IllegalArgumentException("the annotation is not illeagl");
            }
            configuration.addStatement(id, mappedStatement);
            try {
                resolveReturnType(mappedStatement, method);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void resolveReturnType(MappedStatement mappedStatement, Method method) throws Exception {
        Class<?> returnType = method.getReturnType();
        boolean hasReturnType = true;
        Class rawReturnType = null;
        Class generaReturnType = null;
        boolean hasGeneraReturnType = false;
        boolean returnMap = false;
        boolean returnMany = false;
        if (returnType.isAssignableFrom(Void.class)) {
            hasReturnType = false;
            returnType = returnType;
            generaReturnType = returnType;
        } else if (returnType.isAssignableFrom(Integer.class) ||
                returnType.isAssignableFrom(Long.class)
                || returnType.isAssignableFrom(Double.class) || returnType.isAssignableFrom(Float.class) || returnType.isAssignableFrom(Date.class)
                || returnType.isAssignableFrom(Float.class) || returnType.isPrimitive()) {
            hasReturnType = false;
            returnType = returnType;
            generaReturnType = returnType;
        } else if (returnType.isAssignableFrom(List.class)) {
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length != 1) {
                    throw new Exception("the method" + method.getName() + "parameterizedType is wrong ,it's a list but has" + actualTypeArguments.length + "parameterized");
                }
                generaReturnType = (Class) actualTypeArguments[0];
                if (generaReturnType.isAssignableFrom(Map.class)) {
                    throw new Exception("the method" + method.getName() + "generalRturnType is map ,sorry i could not deal with this now");
                }
                rawReturnType = List.class;
                returnMany = true;
                hasGeneraReturnType = true;
            }
        } else if (returnType.isAssignableFrom(Map.class)) {
            //如果是map类型,不在处理泛型,默认string,object
            returnType = HashMap.class;
            generaReturnType = HashMap.class;
            hasGeneraReturnType = true;
            hasReturnType = true;
            returnMap = true;
        } else {
            returnType = method.getReturnType();
            generaReturnType = returnType;
            hasGeneraReturnType = false;
        }
        ReturnType returnType1 = new ReturnType(rawReturnType, generaReturnType, hasReturnType, hasGeneraReturnType, returnMap, returnMany);
        mappedStatement.setReturnType(returnType1);
    }

    private Method resolveRelatedMethod(String methodName, Method method, Class relatedClass) throws NoSuchMethodException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method relatedClassMethod = relatedClass.getMethod(methodName, parameterTypes);
        return relatedClassMethod;
    }
}
