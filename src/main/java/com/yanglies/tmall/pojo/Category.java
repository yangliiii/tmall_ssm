package com.yanglies.tmall.pojo;

/**
 * lies, please leave something
 * 分类
 * @author lies
 * @Createdon 2017/10/31 14:49.
 * @ProjectName tmall_ssm
 */
public class Category {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
