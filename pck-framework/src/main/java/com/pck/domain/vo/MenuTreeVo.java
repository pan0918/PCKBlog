package com.pck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {

    private static final long serialVersionUID = 1L;

    // 树节点Id
    private Long id;

    // 节点名称
    private String label;

    // 父节点Id
    private Long parentId;

    // 子节点集合
    private List<MenuTreeVo> children;
}
