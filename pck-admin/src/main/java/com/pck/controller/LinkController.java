package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Link;
import com.pck.domain.vo.PageVo;
import com.pck.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 分页显示友链
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listAllLink(Integer pageNum, Integer pageSize, String name, String status) {
        PageVo pageVo = linkService.listAllLink(pageNum, pageSize, name, status);

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增友链
     * @param link
     * @return
     */
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link) {
        linkService.save(link);

        return ResponseResult.okResult();
    }

    /**
     * 修改友链前回显友链内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id) {
        Link link = linkService.getById(id);

        return ResponseResult.okResult(link);
    }

    /**
     * 修改友链状态
     * @param link
     * @return
     */
    @PutMapping("/changeLinkStatus")
    public ResponseResult updateLinkStatus(@RequestBody Link link) {
        linkService.updateById(link);

        return ResponseResult.okResult();
    }

    /**
     * 修改友链
     * @param link
     * @return
     */
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link) {
        linkService.updateById(link);

        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);

        return ResponseResult.okResult();
    }
}
