package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    // 处理图片上传
    @PostMapping("/upload")
    @SystemLog(businessName = "用户图片上传更新")
    public ResponseResult uploadImg(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
