package com.yanglies.tmall.comparator;

import com.yanglies.tmall.pojo.Product;

import java.util.Comparator;

/**
 * lies, please leave something
 *
 *  评价数量排序
 * @author lies
 * @Createdon 2018/1/18 17:40.
 * @ProjectName tmall_ssm
 */
public class ProductReviewComparator implements Comparator<Product> {


    @Override
    public int compare(Product p1, Product p2) {
        return p1.getReviewCount() - p2.getReviewCount();
    }
}
