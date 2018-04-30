package util;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/4/24.
 */
public class ParamUtil {


    public static Object getValue(Object o, String filed) throws OgnlException {
        if (o instanceof Integer || o instanceof Boolean || o instanceof Long || o instanceof String || o instanceof Date) {
            return o;
        }
        if (o instanceof List) {
            try {
                int i = Integer.parseInt(filed);
                return  ((List) o).get(i);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("the obejct is a list,but the filed is not a index");
            }
        }
        return OgnlCache.getValue(filed, o);
    }

    public static class OgnlCache {

        private static OgnlContext context = new OgnlContext();
        private static Map<String, Object> cacheMap = new ConcurrentHashMap();

        public static Object getValue(String expression, Object param) throws OgnlException {
            context.setRoot(param);

            Object value2 = Ognl.getValue(parseExpression(expression), context, context.getRoot());
            return value2;
        }

        public static Object parseExpression(String expression) throws OgnlException {
            Object o = cacheMap.get(expression);
            if (o == null) {
                o = Ognl.parseExpression(expression);
                cacheMap.put(expression, o);
            }
            return o;
        }
    }
}
