package com.yanglies.tmall.service.impl;

import com.yanglies.tmall.mapper.CategoryMapper;
import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.service.CategoryService;
import com.yanglies.tmall.util.Page;
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

//    @Override
//    public List<Category> list() {
//        return categoryMapper.list();
//    }

    @Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.get(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }
}
