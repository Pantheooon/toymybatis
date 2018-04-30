package parse;

import configuration.Configuration;
import exception.NoAnotationException;
import sql.annotation.Mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/20.
 */
public class MapperParse extends Parse {

    private Class aClass;

    public MapperParse(Configuration configuration, Class mapper) {
        super(configuration);
        aClass = mapper;
    }


    @Override
    public void parse() {
        Annotation annotation = aClass.getAnnotation(Mapper.class);
        if (annotation == null) {
            throw new NoAnotationException("could not find SqlProvider annotation in " + aClass.getName() + "pls check out");
        }
        if (annotation instanceof Mapper) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                Parse statementParse = new StatementParse(configuration, method,aClass);
                statementParse.parse();

            }
        }
    }
}
