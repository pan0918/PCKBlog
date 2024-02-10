package com.pck.controller;

import com.pck.annotation.SystemLog;
import com.pck.domain.ResponseResult;
import com.pck.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    // 查询所有友情链接
    @GetMapping("/getAllLink")
    @SystemLog(businessName = "友情链接获取")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
