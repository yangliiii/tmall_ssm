package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.pojo.Property;

import java.util.List;

/**
 * lies, please leave something
 *  提供CRUD方法
 * @author lies
 * @Createdon 2017/11/3 15:22.
 * @ProjectName tmall_ssm
 */

public interface PropertyService {
    void add(Property property);

    void  delete(int id);

    void update(Property property);

    Property get(int id);

    List list(int cid);
}
