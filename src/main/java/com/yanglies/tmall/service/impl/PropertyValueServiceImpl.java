package com.yanglies.tmall.service.impl;

import com.yanglies.tmall.mapper.PropertyMapper;
import com.yanglies.tmall.mapper.PropertyValueMapper;
import com.yanglies.tmall.pojo.Product;
import com.yanglies.tmall.pojo.Property;
import com.yanglies.tmall.pojo.PropertyValue;
import com.yanglies.tmall.pojo.PropertyValueExample;
import com.yanglies.tmall.service.PropertyService;
import com.yanglies.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lies, please leave something
 *  PropertyValue 的CRUD实现。
 * @author lies
 * @Createdon 2017/11/6 16:58.
 * @ProjectName tmall_ssm
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService
{

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list(product.getId());
        for(Property property : properties){
            PropertyValue propertyValue = get(property.getId(),product.getId());
            if (null == propertyValue){
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }

    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        //使用PropertyValueExample
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        if(propertyValues.isEmpty()){
            return null;
        }
        return propertyValues.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : result) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return result;
    }
}
