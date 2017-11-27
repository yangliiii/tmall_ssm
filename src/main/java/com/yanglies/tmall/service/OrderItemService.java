package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Order;
import com.yanglies.tmall.pojo.OrderItem;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/13 14:34.
 * @ProjectName tmall_ssm
 */
public interface OrderItemService {
    void add(OrderItem c);

    void delete(int id);

    void update(OrderItem c);

    OrderItem get(int id);

    List list();

    void fill(List<Order> os);

    void fill(Order o);

    //根据产品获取销售数量的方法
    int getSaleCount(int pid);
}
