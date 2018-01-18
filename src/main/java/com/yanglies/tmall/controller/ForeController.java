package com.yanglies.tmall.controller;

import com.yanglies.tmall.comparator.*;
import com.yanglies.tmall.pojo.*;
import com.yanglies.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

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
    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs= categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
         model.addAttribute("cs", cs);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model,User user) {
        String name =  user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        
        if(exist){
            String m ="用户名已经被使用,不能使用";
            model.addAttribute("msg", m);


            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);

        if(null==user){
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }
    @RequestMapping("forelogout")
    public String logout( HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product( int pid, Model model) {
        Product p = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);

        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    /**
     * 校验用户是否登录
     * @param session
     * @return
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(null != user){
            return "success";
        }
        return "fail";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam String name, @RequestParam String password, HttpSession session){

        name = HtmlUtils.htmlEscape(name);
        //获取用户名和密码，是否存在
        User user = userService.get(name, password);
        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    /**
     * 产品页展示+排序
     * @param cid
     * @param sort
     * @param model
     * @return
     */
    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model){
        //根据cid获取到对应的Category
        Category c = categoryService.get(cid);
        //为该Category填充产品
        productService.fill(c);
        //填充销量和评价数量
        productService.setSaleAndReviewNumber(c.getProducts());
        //排序判断
        if(null != sort){
            switch (sort) {
                case  "review" :
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date"  :
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount" :
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price" :
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
                case "all" :
                    Collections.sort(c.getProducts(), new ProductAllComparator());
            }
        }
        model.addAttribute("c", c);
        return "fore/category";
    }
}
