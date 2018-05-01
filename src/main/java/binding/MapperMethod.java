package binding;

import mapping.MappedStatement;
import mapping.ParamMap;
import parse.DefaultSqlHandler;
import parse.SqlHandler;
import session.SqlSession;
import sql.annotation.Param;
import util.ParamUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/15.
 */
public class MapperMethod {
    private Method method;
    private SqlSession sqlSession;

    public MapperMethod(Method method, SqlSession sqlSession) {
        this.method = method;
        this.sqlSession = sqlSession;
    }

    public Object excute(Object[] objects, MappedStatement mappedStatement) throws Exception {
        //此时map中封装了sql语句占位符的表达式,键为语句出现的顺序
        Map<Integer, String> map = new HashMap<>();
        //含有占位符的sql语句
        String sql = resolveParamAndSql(map, mappedStatement, objects);
        String statement = method.getDeclaringClass().getName() + "." + method.getName();
        Map<Integer, ParamMap> parameters = getParameters(objects, map);
        try {
            switch (mappedStatement.getSqlCommandType()) {
                case SELECT:
                    if (mappedStatement.getReturnType().isReturnMany()) {
                        return sqlSession.select(statement, parameters, sql);
                    } else {
                        //返回一个
                        return sqlSession.selectOne(statement, parameters, sql);
                    }
                case INSERT:
                    if (objects.length != 1 && mappedStatement.getGeratekey()) {
                        throw new Exception("the method " + mappedStatement.getRelatedMethod().getName() + "has multi param and it need generate keys");
                    }
                    if (objects == null) {
                        sqlSession.insert(statement, parameters, sql, null);
                    } else {
                        sqlSession.insert(statement, parameters, sql, objects[0]);
                    }
                    return null;
                case DELETE:
                case UPDATE:
                    sqlSession.updateOrDelete(parameters, sql);
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String resolveParamAndSql(Map<Integer, String> map, MappedStatement mappedStatement, Object[] objects) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String sql = (String) mappedStatement.getRelatedMethod().invoke(mappedStatement.getRelatedClass().newInstance(), objects);
        SqlHandler handler = new DefaultSqlHandler("#{", "}");
        return handler.HandleParam(sql, map);
    }

    private Map<Integer, ParamMap> getParameters(Object[] objects, Map<Integer, String> map) throws Exception {
        Map<Integer, ParamMap> paramMaps = new HashMap();
        if (objects.length == 0) {
            if (map.size() != 0) {
                throw new Exception("method param is 0,but the sql param is not 0");
            }
            return paramMaps;
        }
        if (map.size() == 1) {
            ParamMap param = new ParamMap();
            param.setName(map.get(0));
            param.setIndex(0);
            param.setValue(ParamUtil.getValue(objects[0], map.get(0)));
            paramMaps.put(0, param);
            return paramMaps;
        }

        //如果只有一个参数
        if (objects.length == 1) {

            for (int k = 0; k < map.size(); k++) {
                String expression = map.get(k);
                Object value = ParamUtil.getValue(objects[0], expression);
                ParamMap paramMap = new ParamMap();
                paramMap.setValue(value);
                paramMap.setIndex(k);
                paramMap.setName(expression);
                paramMaps.put(k, paramMap);
            }
        } else {
            //获取method的annotation注解
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (parameterAnnotations.length != objects.length) {
                throw new Exception("the annotations num is " + parameterAnnotations.length + "but the param num is" + objects.length);
            }
            //此处存在param注解数量和参数数量不一致的情况
            Map<String, Object> context = new HashMap<String, Object>();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    Annotation annotation = parameterAnnotations[i][j];
                    if (annotation instanceof Param) {
                        String name = ((Param) annotation).value();
                        Object value = objects[i];
                        context.put(name, value);
                    }
                }
            }
            for (int k = 0; k < map.size(); k++) {
                String expression = map.get(k);
                Object value = ParamUtil.getValue(context, expression);
                ParamMap paramMap = new ParamMap();
                paramMap.setValue(value);
                paramMap.setIndex(k);
                paramMap.setName(expression);
                paramMaps.put(k, paramMap);
            }
        }

        return paramMaps;
    }

}
