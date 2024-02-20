package com.pck.domain.vo;

import com.pck.domain.entity.Role;
import com.pck.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {

    private List<String> roleIds;

    private List<Role> roles;

    private User user;
}
