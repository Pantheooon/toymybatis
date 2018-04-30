package mapping;

import typeHandler.*;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/14.
 */
public class ParamMap {

    private String name;
    private TypeHandler typeHandler;
    private Integer index;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        resolveTypeHandler(value);
        this.value = value;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(TypeHandler typeHandler) {
        this.typeHandler = typeHandler;
    }

    private void resolveTypeHandler(Object value) {
        if (Void.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = null;
        } else if (int.class.isAssignableFrom(value.getClass()) || Integer.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = new IntegerHandler();
        } else if (long.class.isAssignableFrom(value.getClass()) || Long.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = new LongHandler();
        } else if (String.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = new StringHandler();
        } else if (double.class.isAssignableFrom(value.getClass()) || Double.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = new DoubleHandler();
        } else if (float.class.isAssignableFrom(value.getClass()) || Float.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = new FloatHandler();
        } else if (Date.class.isAssignableFrom(value.getClass())){
            this.typeHandler = new DateTypeHandler();
        }else if (Object.class.isAssignableFrom(value.getClass())) {
            this.typeHandler = new ObjectHandler();
        }
    }
}
