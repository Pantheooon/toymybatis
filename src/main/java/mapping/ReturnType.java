package mapping;

import typeHandler.TypeHandler;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */
public class ReturnType<T> {

    private Class<T> rawReturnType;
    private Class<T> generalReturnType;
    private boolean hasReturnType;
    private boolean hasGeneralReturnType;
    private boolean returnMap;
    private boolean returnMany;

    public ReturnType(Class<T> rawReturnType, Class<T> generalReturnType, boolean hasReturnType, boolean hasGeneralReturnType, boolean returnMap, boolean returnMany) {
        this.rawReturnType = rawReturnType;
        this.generalReturnType = generalReturnType;
        this.hasReturnType = hasReturnType;
        this.hasGeneralReturnType = hasGeneralReturnType;
        this.returnMany = returnMany;
        this.returnMap = returnMap;
    }

    public Class<T> getRawReturnType() {
        return rawReturnType;
    }

    public void setRawReturnType(Class<T> rawReturnType) {
        this.rawReturnType = rawReturnType;
    }

    public Class<T> getGeneralReturnType() {
        return generalReturnType;
    }

    public void setGeneralReturnType(Class<T> generalReturnType) {
        this.generalReturnType = generalReturnType;
    }

    public boolean isHasReturnType() {
        return hasReturnType;
    }

    public void setHasReturnType(boolean hasReturnType) {
        this.hasReturnType = hasReturnType;
    }

    public boolean isHasGeneralReturnType() {
        return hasGeneralReturnType;
    }

    public void setHasGeneralReturnType(boolean hasGeneralReturnType) {
        this.hasGeneralReturnType = hasGeneralReturnType;
    }

    public boolean isReturnMap() {
        return returnMap;
    }

    public void setReturnMap(boolean returnMap) {
        this.returnMap = returnMap;
    }

    public boolean isReturnMany() {
        return returnMany;
    }

    public void setReturnMany(boolean returnMany) {
        this.returnMany = returnMany;
    }
}
