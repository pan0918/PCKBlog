package com.pck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeSelectVo {

    private List<Long> checkedKeys;

    private List<MenuTreeVo> menus;
}
