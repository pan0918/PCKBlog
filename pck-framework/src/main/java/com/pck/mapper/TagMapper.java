package com.pck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pck.domain.entity.Tag;
import org.apache.ibatis.annotations.Param;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author AVANTI
 * @since 2024-02-12 16:37:20
 */
public interface TagMapper extends BaseMapper<Tag> {

    // 逻辑删除, 将Id值对应的Flag值修改一下
    void updateFlag(Long id);


}
