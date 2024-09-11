package com.ray.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.constant.CategoryConstants;
import com.ray.domain.Category;
import com.ray.exception.BusinessException;
import com.ray.mapper.CategoryMapper;
import com.ray.service.CategoryService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
*/
@Service
@CacheConfig(cacheNames = "com.ray.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Cacheable(key = CategoryConstants.ALL_CATEGORY)
    public List<Category> queryAllCategoryList() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .orderByDesc(Category::getSeq,Category::getCreateTime)
        );
    }

    @Override
    public List<Category> queryRootCategoryList() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId,0)
                .orderByDesc(Category::getSeq)
        );
    }

    @Override
    @CacheEvict(key = CategoryConstants.ALL_CATEGORY)
    public Integer addCategory(Category category) {
        // 创建时间
        category.setCreateTime(new Date());
        // 更新时间
        category.setUpdateTime(new Date());
        return categoryMapper.insert(category);
    }

    @Override
    @CacheEvict(key = CategoryConstants.ALL_CATEGORY)
    public Integer modifyCategory(Category category) {
        // 获取商品类目标识
        Long categoryId = category.getCategoryId();
        // 查询类目信息
        Category oldCategory = categoryMapper.selectById(categoryId);
        // 获取类目原来的父节点ID
        Long oldParentId = oldCategory.getParentId();
        // 查询当前类目的子类目数量
        Long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, categoryId)
        );
        // 一级类目 -> 二级类目
        if (0 == oldParentId && 0 != category.getParentId()){
            // 判断类目是否包含子类目，如果子类目数量为0则可以修改，否则相反
            if (0 != count) {
                // 说明当前类目包含子类目，则不可修改
                throw new BusinessException("类目包含子类目，不可修改");
            }
        }
        // 二级类目 -> 一级类目
        if (0 != oldParentId && ObjectUtil.isNull(category.getParentId())) {
            category.setParentId(0L);
        }
        category.setUpdateTime(new Date());
        return categoryMapper.updateById(category);
    }

    @Override
    @CacheEvict(key = CategoryConstants.ALL_CATEGORY)
    public Integer removeCategoryById(Long categoryId) {
        // 查询当前类目的子类目
        List<Category> child = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, categoryId)
        );
        // 判断子类目是否有值，如果有值，则不可删除
        if (!CollectionUtils.isEmpty(child) && child.size() != 0) {
            // 说明当前类目包含子类目，不可删除
            throw new BusinessException("当前类目包含子类目，不可删除");
        }
        return categoryMapper.deleteById(categoryId);
    }
}
