package com.ray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ray.domain.SysUser;
import com.ray.dto.SysUserAddOrUpdateParam;
import com.ray.vo.SysUserDetailVo;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
*/
public interface SysUserService extends IService<SysUser>{

        /**
         * 新增管理员
         * @param addOrUpdateParam 管理员参数
         * @return
         */
        Integer addSysUser(SysUserAddOrUpdateParam addOrUpdateParam);

        /**
         * 根据管理员ID查询管理员详情
         * @param userId
         * @return
         */
        SysUserDetailVo querySysUserDetailByUserId(Long userId);

        /**
         * 修改管理员信息
         * @param addOrUpdateParam
         * @return
         */
        Integer modifySysUser(SysUserAddOrUpdateParam addOrUpdateParam);

        /**
         * 批量删除管理员
         * @param userIds 管理员ID集合
         * @return 删除数量
         */
        Integer removeSysUsersByUserIdList(List<Long> userIds);

}
