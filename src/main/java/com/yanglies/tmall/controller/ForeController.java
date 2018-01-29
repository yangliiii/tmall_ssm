package com.yanglies.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.yanglies.tmall.comparator.*;
import com.yanglies.tmall.pojo.*;
import com.yanglies.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

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
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if (exist) {
            String m = "用户名已经被使用,不能使用";
            model.addAttribute("msg", m);


            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product(int pid, Model model) {
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
     *
     * @param session
     * @return
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam String name, @RequestParam String password, HttpSession session) {

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
     *
     * @param cid
     * @param sort
     * @param model
     * @return
     */
    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
        //根据cid获取到对应的Category
        Category c = categoryService.get(cid);
        //为该Category填充产品
        productService.fill(c);
        //填充销量和评价数量
        productService.setSaleAndReviewNumber(c.getProducts());
        //排序判断
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
            }
        }
        model.addAttribute("c", c);
        return "fore/category";
    }

    /**
     * 查询页
     *
     * @param keyword 查询内容
     * @param model
     * @return
     */
    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
        //更具查询条件查询出对应产品的前20个。
        PageHelper.offsetPage(0, 20);
        List<Product> ps = productService.search(keyword);
        //为查询出的产品填充评价以及销量
        productService.setSaleAndReviewNumber(ps);
        model.addAttribute("ps", ps);
        //跳转到searchResult.jsp页面
        return "fore/searchResult";
    }

    /**
     *
     * @param pid   商品ID
     * @param num   该订单项中的产品数量是多少
     * @param session 获取用户信息
     * @return
     */
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        Product p = productService.get(pid);
        int oiid = 0;
        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId().intValue() == p.getId().intValue()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }

        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return "redirect:forebuy?oiid=" + oiid;
    }

    @RequestMapping("forebuy")
    public String buy( Model model,String[] oiid,HttpSession session){
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;

        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.get(id);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }

        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    /**
     * 加入购物车
     *
     * @param pid
     * @param num
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model,HttpSession session) {
        Product p = productService.get(pid);
        User user =(User)  session.getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }
        return "success";
    }

    /**
     * 查看购物车
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session){
        //从session中获取uid,查询是否登录
        User user = (User)session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois",ois);
        return "fore/cart";
    }

    /**
     * 结算页面操作，调整订单数量
     *
     * @param model
     * @param session
     * @param pid
     * @param number
     * @return
     */
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Model model, HttpSession session, int pid, int number){
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi :
                ois) {
            if (oi.getProduct().getId().intValue() == pid) {
                oi.setNumber(number);
                //更新当前OrderItem项的数量
                orderItemService.update(oi);
                break;
            }
        }
        return "cuccess";
    }

    /**
     * 删除OrderItem项
     *
     * @param session
     * @param oiid
     * @return
     */
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(HttpSession session, int oiid){
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }
        orderItemService.delete(oiid);
        return "cuccess";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(Model model, Order order, HttpSession session){
        User user = (User) session.getAttribute("user");
        // 订单号
        String orderCode = new SimpleDateFormat().format(new Date()) + RandomUtils.nextInt(10000);
        order.setUid(user.getId());
        order.setCreateDate(new Date());
        order.setOrderCode(orderCode);


        return "";
    }
}
