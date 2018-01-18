package com.yanglies.tmall.comparator;

import com.yanglies.tmall.pojo.Product;

import java.util.Comparator;

/**
 * lies, please leave something
 *  综合比较器 实现Comparator<>接口
 *
 * @author lies
 * @Createdon 2018/1/18 17:28.
 * @ProjectName tmall_ssm
 */
public class ProductAllComparator implements Comparator<Product> {
    /**
     * 销量x评价排序
     * @param p1
     * @param p2
     * @return
     */
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()*p2.getSaleCount() - p1.getReviewCount()*p1.getSaleCount();
    }
}
