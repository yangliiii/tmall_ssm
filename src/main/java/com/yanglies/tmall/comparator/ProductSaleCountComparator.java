package com.yanglies.tmall.comparator;

import com.yanglies.tmall.pojo.Product;

import java.util.Comparator;

/**
 * lies, please leave something
 *  销量排序
 * @author lies
 * @Createdon 2018/1/18 17:44.
 * @ProjectName tmall_ssm
 */
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount() - p1.getSaleCount();
    }
}
