package com.yanglies.tmall.service;

import com.yanglies.tmall.pojo.Review;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/27 17:01.
 * @ProjectName tmall_ssm
 */
public interface ReviewService {

    //CRUD
    void add(Review c);

    void delete(int id);

    void update(Review c);

    Review get(int id);

    List list(int pid);

    int getCount(int pid);

}
