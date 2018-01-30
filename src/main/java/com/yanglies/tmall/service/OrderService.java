package com.yanglies.tmall.service;
 
import java.util.List;

import com.yanglies.tmall.pojo.Order;
import com.yanglies.tmall.pojo.OrderItem;

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

    float add(Order order, List<OrderItem> ois);

    List list(int uid, String excludedStatus) ;
}