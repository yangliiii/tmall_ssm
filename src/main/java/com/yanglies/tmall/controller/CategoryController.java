package com.yanglies.tmall.controller;

import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/10/31 14:57.
 * @ProjectName tmall_ssm
 */

//申明为一个控制器
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model){
        List<Category> cs= categoryService.list();
        model.addAttribute("cs", cs);
        return "admin/listCategory";
    }
}
