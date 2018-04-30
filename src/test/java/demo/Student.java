package demo;

import sql.annotation.Mapper;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/27.
 */
public class Student {

    public Integer id;
    private String name;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
