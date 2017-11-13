package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Order;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/13 14:28.
 * @ProjectName tmall_ssm
 */
public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order c);

    void delete(int id);

    void update(Order c);

    Order get(int id);

    List list();
}
