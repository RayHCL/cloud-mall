package com.ray.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.constant.SysMenuConstants;
import com.ray.domain.SysMenu;
import com.ray.exception.BusinessException;
import com.ray.mapper.SysMenuMapper;
import com.ray.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @description
 * @date 2024/08/11
*/
@Service
@CacheConfig(cacheNames = "com.ray.service.impl.SysMenuServiceImpl")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    @Cacheable(key = "#userId")
    public Set<SysMenu> queryUserMenus(Long userId) {
        // 根据用户ID查询菜单集合
        Set<SysMenu> sysMenus = sysMenuMapper.selectUserMenusByUserId(userId);
        // 判断菜单集合是否有值
        if (!CollectionUtils.isEmpty(sysMenus) && sysMenus.size() != 0) {
            // 将菜单集合转换为树结构
            sysMenus = translateMenusToTree(sysMenus,0L);
        }
        return sysMenus;
    }

    /**
     * 将菜单集合转换为树结构
     * @param sysMenus
     * @param pid
     * @return
     */
    private Set<SysMenu> translateMenusToTree(Set<SysMenu> sysMenus, Long pid) {
        // 获取菜单的根节点集合
        Set<SysMenu> roots = sysMenus.stream()
                .filter(menu -> menu.getParentId().equals(pid))
                .collect(Collectors.toSet());
        // 循环遍历父节点集合，根据指定父节点id查询子节点集合
        roots.forEach(r -> r.setList(translateMenusToTree(sysMenus,r.getMenuId())));
        return roots;
    }


    @Override
    @Cacheable(key = SysMenuConstants.ALL_MENUS)
    public List<SysMenu> queryAllSysMenuList() {
        return sysMenuMapper.selectList(null);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean save(SysMenu sysMenu) {
        return super.save(sysMenu);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateById(SysMenu sysMenu) {
        return super.updateById(sysMenu);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean removeById(Serializable id) {
        // 查询当前权限节点的子节点
        List<SysMenu> childMenuList = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, id)
        );
        // 判断是否包含子节点
        if (!CollectionUtils.isEmpty(childMenuList) && childMenuList.size() != 0) {
            throw new BusinessException("当前节点包含子节点，不可删除");
        }
        return super.removeById(id);
    }
}
