package com.yanglies.tmall.controller;

import com.yanglies.tmall.pojo.*;
import com.yanglies.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/13 16:19.
 * @ProjectName tmall_ssm
 */
@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping("foreproduct")
    public String product(int pid,Model model){
        //根据传入的产品ID获取产品信息
        Product p = productService.get(pid);
        //获取产品p之后，获取对应的SingleImage 和 DetailImage
        List<ProductImage> productSingleImages = productImageService.list(p.getId(),ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(),ProductImageService.type_detail);
        p.setProductSingleImage(productSingleImages);
        p.setProductDetailImage(productDetailImages);

        //获取产品的属性信息
        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        //将获取到的信息放入request中
        model.addAttribute("p" , p);
        model.addAttribute("pvs",pvs);
        model.addAttribute("reviews",reviews);
        return "fore/product";
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name ,
                        @RequestParam("password") String pwd,
                        Model model,
                        HttpSession session){
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,pwd);
        if (null == user) {
            model.addAttribute("msg","用户名和密码错误，请重新输入！");
            return "fore/login";
        }
        session.setAttribute("user",user);
        return "redirect:forehome";
    }

    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs= categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model,User user){
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        //用户名如果存在
        if (exist){
            String m = "用户名存在，请重新输入！";
            model.addAttribute("m",m);
            model.addAttribute("user",null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

}
