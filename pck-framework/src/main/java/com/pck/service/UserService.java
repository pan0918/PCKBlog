package com.pck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pck.domain.ResponseResult;
import com.pck.domain.entity.User;
import com.pck.domain.vo.PageVo;
import com.pck.domain.vo.UserRoleVo;


/**
 * 用户表(User)表服务接口
 *
 * @author AVANTI
 * @since 2024-02-06 22:41:40
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    PageVo listAllUser(Integer pageNum, Integer pageSize, String userName, String phoneNumber, String status);

    void addUser(User user);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    UserRoleVo getUserById(Long id);

    void updateUser(User user);

    void deleteUser(Long id);
}
