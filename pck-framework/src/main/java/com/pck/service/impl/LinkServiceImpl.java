package com.pck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pck.constants.SystemConstants;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.Link;
import com.pck.domain.vo.LinkVo;
import com.pck.mapper.LinkMapper;
import com.pck.service.LinkService;
import com.pck.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author AVANTI
 * @since 2024-02-05 10:48:04
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        // 获取审核通过的所有友情链接
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> lists = list(lambdaQueryWrapper);

        // 封装vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(lists, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
