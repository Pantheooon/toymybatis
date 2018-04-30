package sqlTest;

import demo.IStudentDao;
import org.junit.Test;
import sql.annotation.Param;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Administrator on 2018/4/17.
 */
public class SqlTest {


    @Test
    public void test2() {
//        int a = 10;
//        boolean primitive = a.class.isPrimitive();
//        System.out.println(primitive);

    }


    @Test
    public void test3() throws ClassNotFoundException, IOException {
        URL systemResource = ClassLoader.getSystemResource("");
        URL resource = this.getClass().getResource("/");
        String path = resource.getPath();
        File file = new File(resource.getFile());

        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file.isDirectory()) {
                File[] files1 = file.listFiles();
                for (File file2 : files1) {
//                    File parentFile = file2.getParentFile();
//                    System.out.println(parentFile);
//                    System.out.println(file2.getAbsolutePath());
                    String path1 = file2.getPath();
                    String name = file2.getName();
                    System.out.println(name);
                    System.out.println(path1.substring(path1.lastIndexOf(File.separator) + 1, path1.length()));
                }
            }
        }
    }


    @Test
    public void test44() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        File xFile = new File("D:\\github\\toymybatis\\target\\test-classes\\");
        URL xUrl = xFile.toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{xUrl});
        Class xClass = classLoader.loadClass("com.duia.mapper.mapper");
        System.out.println(xClass.getTypeName());

    }

    @Test
    public void test444() {
        Method[] methods = IStudentDao.class.getMethods();
        Method method = methods[0];
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof Param) {
                    String value = ((Param) annotation).value();
                    System.out.println(value);
                }
            }
        }
    }
}