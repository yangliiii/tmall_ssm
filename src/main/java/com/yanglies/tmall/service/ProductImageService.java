package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.ProductImage;

import java.util.List;

/**
 * lies, please leave something
 *  提供产品图片的CRUD
 * @author lies
 * @Createdon 2017/11/6 15:39.
 * @ProjectName tmall_ssm
 */
public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";

    void add(ProductImage productImage);

    void delete(int id);

    void update(ProductImage productImage);

    ProductImage get(int id);

    List list(int pid,String type);

}
