package com.yanglies.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yanglies.tmall.pojo.User;
import com.yanglies.tmall.service.UserService;
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
 * @Createdon 2017/11/13 14:05.
 * @ProjectName tmall_ssm
 */

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        //获取分页参数
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //获取全部User记录
        List<User> users = userService.list();
        //获取分页总数
        int total = (int)new PageInfo<>(users).getTotal();
        page.setTotal(total);

        model.addAttribute("users",users);
        model.addAttribute("page",page);
        return "admin/listUser";
    }
}
