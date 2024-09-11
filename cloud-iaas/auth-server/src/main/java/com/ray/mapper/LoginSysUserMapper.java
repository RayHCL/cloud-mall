package com.ray.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ray.domain.LoginSysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author Ray
 * @description 后台管理员用户mapper接口
 * @date 2024/08/04
 */
@Mapper
public interface LoginSysUserMapper extends BaseMapper<LoginSysUser> {
    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    Set<String> selectPermsBySysUserId(Long userId);
}
