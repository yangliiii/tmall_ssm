package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.User;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/13 13:56.
 * @ProjectName tmall_ssm
 */
public interface UserService {

    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List list();

    boolean isExist(String username);
}
