package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Category;
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

    void fill(List<Category> cs);

    void fill(Category c);

    void fillByRow(List<Category> cs);

    //增加产品的销量和评价数量的方法

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);
}
