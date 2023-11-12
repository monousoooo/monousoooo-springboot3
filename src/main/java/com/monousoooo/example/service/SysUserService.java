package com.monousoooo.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monousoooo.example.model.dto.UserDTO;
import com.monousoooo.example.model.dto.UserInfo;
import com.monousoooo.example.model.entity.SysUser;
import com.monousoooo.example.model.vo.UserVO;
import com.monousoooo.example.util.Response;

public interface SysUserService extends IService<SysUser>{

    UserInfo findUserInfo(SysUser sysUser);

    IPage getUsersWithRolePage(Page page, UserDTO userDTO);

    Boolean deleteUserByIds(Long[] ids);

    Response<Boolean> updateUserInfo(UserDTO userDto);

    Boolean updateUser(UserDTO userDto);

    UserVO selectUserVoById(Long id);

    Boolean saveUser(UserDTO userDto);

    Response<Boolean> registerUser(UserDTO userDto);

    Response changePassword(UserDTO userDto);

    Response checkPassword(String password);
}
