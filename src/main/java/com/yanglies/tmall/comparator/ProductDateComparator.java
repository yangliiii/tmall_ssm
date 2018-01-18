package com.yanglies.tmall.comparator;

import com.yanglies.tmall.pojo.Product;

import java.util.Comparator;

/**
 * lies, please leave something
 *  新品放在前面（创建时间最晚）
 * @author lies
 * @Createdon 2018/1/18 17:42.
 * @ProjectName tmall_ssm
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getCreateDate().compareTo(p1.getCreateDate());
    }
}
