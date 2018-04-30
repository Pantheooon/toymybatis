package util;

import sql.annotation.Mapper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * 用于读取mapper接口
 */
public class ClassesResolver {

    private String root;

    public Set<Class> resoveClasses() throws Exception {
        URL resource = this.getClass().getResource("/");
        root = resource.getPath();
        File file = new File(resource.getFile());
        Set<Class> set = new HashSet<>();
        findClasses(file, set);
        return set;
    }

    private void findClasses(File file, Set<Class> set) throws Exception {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length != 0) {
                for (File file1 : files) {
                    findClasses(file1, set);
                }
            }
        } else {
            if (file.canExecute()) {
                MyClassLoader myClassLoader = new MyClassLoader(root);
                String path = file.getPath();
                String substring = path.substring(root.length() - 1, path.length());
                String s = substring.replaceAll("\\\\", ".");
                String substring1 = s.substring(0, s.indexOf(".class"));
                Class<?> aClass = myClassLoader.loadClass(substring1);
                if (aClass.getAnnotation(Mapper.class) != null) {
                    if(set.contains(aClass)){
                        throw new Exception("the class named "+aClass.getName()+"has been loaded");
                    }
                    set.add(aClass);
                }

            }
        }
    }

}
