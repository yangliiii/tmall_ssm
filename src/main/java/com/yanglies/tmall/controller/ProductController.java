package com.yanglies.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.pojo.Product;
import com.yanglies.tmall.service.CategoryService;
import com.yanglies.tmall.service.ProductService;
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
 * @Createdon 2017/11/6 14:07.
 * @ProjectName tmall_ssm
 */
@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(int id,Model model){
        //获取该Product
        Product product = productService.get(id);
        //根据获取的product,从而得到对应的category
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("product",product);

        return "admin/editProduct";
    }

    @RequestMapping("admin_product_add")
    public String add(Product product){
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page){
        //获取该Product对应的Category
        Category category = categoryService.get(cid);
        //获取分页参数
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //获取List<Product>
        List<Product> productList = productService.list(cid);
        //获取总数
        int total = (int)new PageInfo<>(productList).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());

        model.addAttribute("category",category);
        model.addAttribute("page",page);
        model.addAttribute("productList",productList);

        return "admin/listProduct";
    }
}
