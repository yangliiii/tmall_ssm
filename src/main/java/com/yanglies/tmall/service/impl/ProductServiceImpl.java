package com.yanglies.tmall.service.impl;

import com.yanglies.tmall.mapper.ProductMapper;
import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.pojo.Product;
import com.yanglies.tmall.pojo.ProductExample;
import com.yanglies.tmall.pojo.ProductImage;
import com.yanglies.tmall.service.CategoryService;
import com.yanglies.tmall.service.ProductImageService;
import com.yanglies.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/6 13:48.
 * @ProjectName tmall_ssm
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        setFirstProductImage(product);
        return product;
    }

    @Override
    public List list(int cid) {
        ProductExample productExample = new ProductExample();
        //获取cid字段
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id desc");
        List result = productMapper.selectByExample(productExample);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }

    public void setCategory(Product product){
        int cid = product.getCid();
        Category category = categoryService.get(cid);
        product.setCategory(category);
    }

    public void setCategory(List<Product> products){
        for (Product p :
                products) {
            setCategory(p);
        }
    }
}
