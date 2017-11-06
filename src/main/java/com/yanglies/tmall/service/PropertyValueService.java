package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Product;
import com.yanglies.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * lies, please leave something
 *  产品属性对应的CRUD
 * @author lies
 * @Createdon 2017/11/6 16:45.
 * @ProjectName tmall_ssm
 */
public interface PropertyValueService {

    void init(Product product);

    void update(PropertyValue propertyValue);

    //根据产品ID和属性ID获取PropertyValue
    PropertyValue get(int ptid,int pid);

    List<PropertyValue> list(int pid);
}
