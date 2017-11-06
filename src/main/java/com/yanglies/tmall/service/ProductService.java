package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Product;

import java.util.List;

/**
 * lies, please leave something
 *  提供对Product的CRUD
 * @author lies
 * @Createdon 2017/11/6 13:44.
 * @ProjectName tmall_ssm
 */
public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    //根据cid获取对应的Product
    List list(int cid);

    void setFirstProductImage(Product p);
}
