package com.yanglies.tmall.service.impl;

import java.util.List;

import com.yanglies.tmall.pojo.OrderItem;
import com.yanglies.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanglies.tmall.mapper.OrderMapper;
import com.yanglies.tmall.pojo.Order;
import com.yanglies.tmall.pojo.OrderExample;
import com.yanglies.tmall.pojo.User;
import com.yanglies.tmall.service.OrderService;
import com.yanglies.tmall.service.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Order c) {
        orderMapper.insert(c);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> list(){
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);

    }

    /**
     *
     * @param order
     * @param ois
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public float add(Order order, List<OrderItem> ois) {

        float total = 0;
        add(order);

        // 模拟事务回滚
        if (false) {
            throw new RuntimeException();
        }

        for (OrderItem oi : ois) {
            // 为OrderItem关联上Order项
            oi.setOid(order.getId());
            // 更新OrderItem
            orderItemService.update(oi);
            // 总价
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }

        //返回总价
        return total;
    }


}