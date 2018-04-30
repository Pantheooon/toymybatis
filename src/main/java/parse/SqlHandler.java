package parse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/24.
 */
public abstract class SqlHandler {

    protected String prefix;
    protected String endfix;

    protected SqlHandler(String prefix, String endfix) {
        this.prefix = prefix;
        this.endfix = endfix;
    }

    public abstract String HandleParam(String sql, Map<Integer, String> map);

    public abstract List<String> parseSql(String sql);
}
