package com.pck.controller;

import com.pck.domain.ResponseResult;
import com.pck.domain.dto.EditTagDto;
import com.pck.domain.dto.TagListDto;
import com.pck.domain.entity.Tag;
import com.pck.domain.vo.PageVo;
import com.pck.domain.vo.TagVo;
import com.pck.service.TagService;
import com.pck.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取所有标签
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    /**
     * 新增标签
     * @param tagListDto
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody TagListDto tagListDto) {
        // 封装Tag格式
        Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
        tagService.save(tag);

        return ResponseResult.okResult();
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id) {
        // 逻辑删除, 不能使用MP提供的方法
        tagService.removeTagById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改标签前获取标签内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable Long id) {
        // 获取对应Id的标签
        Tag getTag = tagService.getById(id);
        // 封装Vo
        TagVo tagVo = BeanCopyUtils.copyBean(getTag, TagVo.class);

        return ResponseResult.okResult(tagVo);
    }

    /**
     * 修改标签内容
     * @param editTagDto
     * @return
     */
    @PutMapping
    public ResponseResult updateTag(@RequestBody EditTagDto editTagDto) {
        // 封装tag格式
        Tag tag = BeanCopyUtils.copyBean(editTagDto, Tag.class);
        tagService.updateById(tag);

        return ResponseResult.okResult();
    }


}
