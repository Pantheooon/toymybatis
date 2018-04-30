package demo;

import sql.Sql;

/**
 * Created by Administrator on 2018/4/17.
 */
public class StudentProvider {


    public String findBy(Integer id) {
        Sql sql = new Sql();
        String student = sql.select("id,name").from("student").where("id = #{id}").build();
        return student;
    }

    public String findByIdAndName(Integer id, String name) {
        Sql sql = new Sql();
        String student = sql.select("id,name").from("student").where("id = #{id} and name=#{name}").build();
        return student;
    }

    public String findById(Long id) {
        Sql sql = new Sql();
        String student = sql.select("id,name").from("student").where("id = #{id}").build();
        return student;
    }

    public String updateById(Integer id) {
        Sql sql = new Sql();
        String student = sql.update("student").set("name= 'pmjj' ").where("id = #{id}").build();
        return student;
    }

    public String updateByName(String name) {
        Sql sql = new Sql();
        String student = sql.update("student").set("name= #{name} ").where("id = 17").build();
        return student;
    }

    public String insert(Student student) {
        Sql sql = new Sql();
        String students = sql.insert("student").insertColumn("name,date").values("#{name},#{date}").build();
        return students;
    }
}
