package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.ResponseResult;
import com.pck.domain.dto.TagListDto;
import com.pck.domain.entity.Tag;
import com.pck.domain.vo.PageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-12 16:37:21
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    void removeTagById(Long id);

}
