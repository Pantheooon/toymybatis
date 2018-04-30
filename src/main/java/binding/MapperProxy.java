package binding;

import mapping.MappedStatement;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import session.SqlSession;

import java.lang.reflect.Method;
import java.util.Map;


public class MapperProxy implements MethodInterceptor {
    private SqlSession sqlSession;
    private Enhancer enhancer = new Enhancer();

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (!method.getDeclaringClass().isAssignableFrom(Object.class)) {
            MapperMethod mapperMethod = new MapperMethod(method, sqlSession);
            MappedStatement mappedStatement = sqlSession.getMappedStatement(method);
            if (mappedStatement != null) {
                return mapperMethod.excute(objects, mappedStatement);
            }
        }
        return method.invoke(o, objects);
    }
}
