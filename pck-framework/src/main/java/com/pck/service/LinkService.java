package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-05 10:48:03
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
