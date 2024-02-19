package com.pck.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author AVANTI
 * @since 2024-02-18 23:28:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu {
    //角色ID@TableId
    private Long roleId;
    //菜单ID@TableId
    private Long menuId;



}

