package parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/24.
 */
public class DefaultSqlHandler extends SqlHandler {

    private Integer count = 0;

    public DefaultSqlHandler(String prefix, String endfix) {
        super(prefix, endfix);
    }

    @Override
    public String HandleParam(String sql, Map<Integer, String> map) {

        return handle(sql, map);
    }

    @Override
    public List<String> parseSql(String sql) {
        List<String> result = new ArrayList<>();
        String substring = sql.substring(sql.indexOf("select") + 6, sql.indexOf("from"));
        String[] split = substring.split(",");
        for (String s : split) {
            if (s.contains("as")) {
                String[] ass = s.split(" as ");
                result.add(ass[1]);
            } else if (s.contains("AS")){
                String[] ass = s.split(" AS ");
                result.add(ass[1]);
            }
        }

        return result;
    }

    private String handle(String sql, Map<Integer, String> map) {
        if (sql.indexOf(endfix) == -1) {
            return sql;
        }
        String substring = sql.substring(sql.indexOf(prefix) + 2, sql.indexOf(endfix));
        map.put(count, substring);
        sql = sql.replace(prefix + substring + endfix, "?");
        count++;

        return handle(sql, map);
    }

}
