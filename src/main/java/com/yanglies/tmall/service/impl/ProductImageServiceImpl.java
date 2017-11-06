package com.yanglies.tmall.service.impl;

import com.yanglies.tmall.mapper.ProductImageMapper;
import com.yanglies.tmall.pojo.ProductImage;
import com.yanglies.tmall.pojo.ProductImageExample;
import com.yanglies.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/6 15:43.
 * @ProjectName tmall_ssm
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    //由于产品图片不涉及到修改和编辑，因此暂时不实现
    @Override
    public void update(ProductImage productImage) {
//        productImageMapper.updateByPrimaryKey(productImage);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid, String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        //同时匹配pid和type
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("id desc");

        return productImageMapper.selectByExample(productImageExample);
    }
}
