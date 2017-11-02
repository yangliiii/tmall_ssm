package com.yanglies.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * lies, please leave something
 *
 * @author lies
 * @Createdon 2017/11/2 14:42.
 * @ProjectName tmall_ssm
 */


public class UploadedImageFile {
    //用于接收上传文件的注入
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
