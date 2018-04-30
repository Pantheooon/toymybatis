package sql;

/**
 * Created by Administrator on 2018/4/10.
 */
public class Sql {


    protected StringBuffer sb = new StringBuffer();

    public Sql select(String select) {
        return getSelf("select\t" + select + "\t");
    }

    public Sql from(String from) {
        return getSelf("from\t" + from + "\t");
    }

    public Sql join(String join) {
        return getSelf("join\t" + join + "\t");
    }

    public Sql leftJoin(String join) {
        return getSelf("left\tjoin\t" + join + "\t");
    }

    public Sql where(String where) {
        return getSelf("where\t" + where + "\t");
    }

    public Sql groupBy(String groupBy) {
        return getSelf("group\tby\t" + groupBy + "\t");
    }

    public Sql orderBy(String orderBy, String type) {
        return getSelf("order\tby\t" + orderBy + "\t" + type + "\t");
    }

    public Sql having(String having) {
        return getSelf("having\t" + having + "\t");
    }

    public Sql limit(String start, String end) {
        return getSelf("limit\t" + start + "\t" + end + "\t");
    }

    public Sql limit(String limit) {
        return limit(limit, null);
    }

    public String build() {
        return sb.toString();
    }

    public Sql update(String update) {
        return getSelf("update\t" + update + "\t");
    }

    public Sql set(String set) {
        return getSelf("set\t" + set + "\t");
    }

    public Sql delete(String delete) {
        return getSelf("delete\t" + delete + "\t");
    }

    public Sql insert(String tableName) {
        return getSelf("insert into\t" + tableName + "\t");
    }

    public Sql insertColumn(String columns){
        return getSelf("(\t" + columns + ")\t");
    }
    public Sql values(String into) {
        return getSelf("values(\t" + into + ")\t");
    }



    public Sql getSelf(String sql) {
        sb.append(sql);
        return this;
    }


}