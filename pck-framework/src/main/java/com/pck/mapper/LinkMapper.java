package com.pck.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pck.domain.entity.Link;


/**
 * 友链(Link)表数据库访问层
 *
 * @author AVANTI
 * @since 2024-02-05 10:48:02
 */
public interface LinkMapper extends BaseMapper<Link> {

    void deleteLink(Long id);
}
