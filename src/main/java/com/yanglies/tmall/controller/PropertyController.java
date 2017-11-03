package com.yanglies.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.pojo.Property;
import com.yanglies.tmall.service.CategoryService;
import com.yanglies.tmall.service.PropertyService;
import com.yanglies.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/3 15:40.
 * @ProjectName tmall_ssm
 */
@Controller
@RequestMapping("")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page){
        //获取该条属性所对应的分类
        Category category = categoryService.get(cid);
        //获取分页参数
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //获取List<Property>
        List<Property> properties = propertyService.list(cid);
        //获取property的总记录数
        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&id" + category.getId());

        model.addAttribute("category",category);
        model.addAttribute("page",page);
        model.addAttribute("properties",properties);

        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String add(Property property){
        propertyService.add(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }
}
