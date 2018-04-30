package ognlTest;

import ognl.OgnlException;
import org.junit.Assert;
import org.junit.Test;
import util.ParamUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */
public class OgnlTest {


    @Test
    public void testSingleObject() throws OgnlException {
        Author author = new Author(1, "pmj", "123", "123");
        Object value = ParamUtil.OgnlCache.getValue("id", author);
        Assert.assertEquals(value, 1);

    }

    @Test
    public void testMultiObject() throws OgnlException {
        Map<String, Object> map = new HashMap();
        Author author = new Author(1, "pmj", "123", "123");
        map.put("xx", author);
        Object value = ParamUtil.OgnlCache.getValue("id", map);
        Object value2 = ParamUtil.OgnlCache.getValue("desc", map);
        Assert.assertEquals(value, 1);
        Assert.assertEquals(value2, "desc");
        map.put("id", 1);
    }

    @Test
    public void testReflectUtil() throws OgnlException {
        Object author = new Author(1, "pmj", "123", "123");
        Object id = ParamUtil.getValue(author, "id");
        System.out.println(id);
        List ar = new ArrayList();
        ar.add(10);
        Object value = ParamUtil.getValue(ar, "0");
        Assert.assertEquals(value,10);
        Map map = new HashMap();
        map.put("xxx","aaa");
        map.put("xxx2","aaa");
        Object xxx = ParamUtil.getValue(map, "xxx");
        Assert.assertEquals(xxx,"aaa");
        Map map1 = new HashMap();
        map1.put("asd","asd");
        map.put("asd",map1);
        Object ss = ParamUtil.getValue(map, "asd.asd");
        Assert.assertEquals(ss,"asd");

    }
}
