package binding;

import session.DefaultSqlSession;
import session.SqlSession;

/**
 * Created by Administrator on 2018/4/16.
 */
public class MapperProxyFactory<T> {

    private Class<T> tClass;

    public MapperProxyFactory(Class<T> t) {
        this.tClass = t;
    }

    public T newInstanceProxy(SqlSession sqlSession) {
        MapperProxy proxy = new MapperProxy(sqlSession);
        return (T) proxy.getProxy(tClass);
    }
}
