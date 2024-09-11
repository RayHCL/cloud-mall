package com.ray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ray.domain.Category;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
*/
public interface CategoryService extends IService<Category>{

        /**
         * 查询商品所有类目
         * @return
         */
        List<Category> queryAllCategoryList();

    /**
     * 查询商品一级类目
     * @return
     */
    List<Category> queryRootCategoryList();

    /**
     * 新增商品类目
     * @param category
     * @return
     */
    Integer addCategory(Category category);

    /**
     * 修改商品类目
     * @param category
     * @return
     */
    Integer modifyCategory(Category category);
    /**
     * 删除商品类目
     * @param categoryId
     * @return
     */
    Integer removeCategoryById(Long categoryId);

}
