package com.example.actidemo.model;

import java.io.Serializable;

/**
 * @author hujt49
 * @Description
 * @create 2020-09-15 11:02
 */
public class Person implements Serializable{
    private static final long serialVersionUID = 1624306680706345598L;
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
