package parse;

import configuration.Configuration;
import util.ClassesResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/19.
 */
public class BaseParse extends Parse {

    private Properties properties;

    public BaseParse(Configuration configuration, Properties properties) {
        super(configuration);
        this.properties = properties;
    }


    @Override
    public void parse() {
        configuration.setUserName((String) properties.get("username"));
        configuration.setUrl((String) properties.get("url"));
        configuration.setDriver((String) properties.get("driver"));
        configuration.setPassword((String) properties.get("password"));
        ClassesResolver classesResolver = new ClassesResolver();
        Set<Class> classSet = null;
        try {
            classSet = classesResolver.resoveClasses();
            if (classSet.size()!= 0) {
                for (Class mapper : classSet) {
                    configuration.addMapper(mapper);
                    Parse mapperParse = new MapperParse(configuration, mapper);
                    mapperParse.parse();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
