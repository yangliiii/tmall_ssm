package com.yanglies.tmall.service.impl;

import com.yanglies.tmall.mapper.CategoryMapper;
import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/10/31 14:55.
 * @ProjectName tmall_ssm
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }
}
