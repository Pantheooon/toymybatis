package excutor;

import mapping.MappedStatement;
import mapping.ParamMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/26.
 */
public interface Excutor {

    <T> List<T> select(MappedStatement mappedStatement, Map<Integer, ParamMap> paramMapMap, String sql);

    void update(Map<Integer, ParamMap> paramMapMap, String sql);

    void insert(MappedStatement mappedStatement, Map<Integer, ParamMap> parameters, String sql,Object param);
}
