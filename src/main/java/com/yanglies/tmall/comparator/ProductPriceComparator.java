package com.yanglies.tmall.comparator;

import com.yanglies.tmall.pojo.Product;

import java.util.Comparator;

/**
 * lies, please leave something
 *  价格排序
 * @author lies
 * @Createdon 2018/1/18 17:46.
 * @ProjectName tmall_ssm
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p2.getPromotePrice() - p1.getPromotePrice());
    }
}
