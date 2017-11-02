package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.util.Page;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/10/31 14:53.
 * @ProjectName tmall_ssm
 */
public interface CategoryService {
    List<Category> list(Page page);
    int total();
    //新增
    void add(Category category);
    //删除
    void delete(int id);
    //编辑-获取数据
    Category get(int id);
    //编辑-修改
    void update(Category category);
}
