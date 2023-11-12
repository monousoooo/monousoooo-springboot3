package com.monousoooo.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.monousoooo.example.model.dto.UserDTO;
import com.monousoooo.example.model.entity.SysUser;
import com.monousoooo.example.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    UserVO getUserVoByUsername(String username);

    IPage<UserVO> getUserVosPage(Page page, @Param("query") UserDTO userDTO);

    UserVO getUserVoById(Long id);

    List<UserVO> selectVoList(@Param("query") UserDTO userDTO);

}
