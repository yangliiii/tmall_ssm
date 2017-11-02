package com.yanglies.tmall.controller;

import com.yanglies.tmall.pojo.Category;
import com.yanglies.tmall.service.CategoryService;
import com.yanglies.tmall.util.ImageUtil;
import com.yanglies.tmall.util.Page;
import com.yanglies.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public String list(Model model, Page page){
        List<Category> cs= categoryService.list(page);
        int total = categoryService.total();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }

    /**
     *
     * @param category  页面提交的分类数据
     * @param httpSession   用于获取当前应用的路径
     * @param uploadedImageFile 用于接受上传图片
     */
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession httpSession,
                    UploadedImageFile uploadedImageFile) throws IOException {
        //通过categoryService保存category对象
        categoryService.add(category);
        //通过httpSession 获取存放分类图片的路径
        File imageFolder = new File(
                httpSession.getServletContext().getRealPath("img/category"));
        //创建分类图片的文件名 category.id.jpg
        File image = new File(imageFolder,category.getId() + ".jpg");

        if (!image.getParentFile().exists()) {
            image.getParentFile().mkdirs();
        }
        System.out.println("-----------------上传图片--------------------");
        System.out.println(imageFolder);
        System.out.println(uploadedImageFile);
        System.out.println(uploadedImageFile.getImage());
        System.out.println(image);
        System.out.println("-----------------上传图片--------------------");

        //将浏览器传入的图片放入指定的位置：imageFolder
        uploadedImageFile.getImage().transferTo(image);
        //将传入的图片转换为jpg格式
        BufferedImage bufferedImage = ImageUtil.change2jpg(image);
        ImageIO.write(bufferedImage,"jpg",image);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession httpSession){
        //删除数据库数据
        categoryService.delete(id);
        //删除对应记录的图片
        File imageFolder = new File(
                httpSession.getServletContext().getRealPath("img/category"));
        File imageFile = new File(imageFolder,id + ".jpg");
        imageFile.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String get(int id,Model model){
        Category category;
        category = categoryService.get(id);
        model.addAttribute("category",category);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category,HttpSession httpSession,
                         UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);
        //获取所接收的图片
        MultipartFile multipartFile = uploadedImageFile.getImage();
        //吐过获取没有图片信息，则重新添加
        if(null != multipartFile && !multipartFile.isEmpty()){
            File imageFolder = new File(
                    httpSession.getServletContext().getRealPath("img/category"));

            File imageFile = new File(imageFolder,category.getId() + ".jpg");

            System.out.println("--------修改分类-------");
            System.out.println(imageFolder);
            System.out.println(imageFile);
            System.out.println("--------修改分类-------");

            //将文件存放至指定的位置
            multipartFile.transferTo(imageFile);
            BufferedImage bufferedImage = ImageUtil.change2jpg(imageFile);
            //个人理解：将转换后的bufferedImage 以 jpg 的格式放至 imageFile
            ImageIO.write(bufferedImage,"jpg",imageFile);
        }
        return "redirect:/admin_category_list";
    }
}
