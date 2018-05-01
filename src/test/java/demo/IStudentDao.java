package demo;

import demo.StudentProvider;
import sql.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */
@Mapper(sqlProvider = StudentProvider.class)
public interface IStudentDao {

    @Select("findBy")
    List<Student> findBy(Integer id);

    @Select("findByIdAndName")
    List<Student> findByIdAndName(@Param("id") Integer id, @Param("name") String name);

    @Select("findById")
    Student findById(Long id);

    @Update("updateById")
    void updateById(Integer id);

    @Update("updateByName")
    void updateByName(String pmjj);

    @Insert("insert")
    void insert(Student student);

    @Select("findByMultiParam")
    List<Student> findByMultiParam(@Param("student1") Student student, @Param("student2") Student student2);
}
