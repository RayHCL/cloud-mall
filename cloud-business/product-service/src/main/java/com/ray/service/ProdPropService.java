package com.ray.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ray.domain.ProdProp;
import com.ray.dto.ProdSpecAddOrUpdateParam;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
public interface ProdPropService extends IService<ProdProp> {

    /**
     * 多条件分页查询商品规格
     *
     * @param page
     * @param propName
     * @return
     */
    Page<ProdProp> queryProdSpecPage(Page<ProdProp> page, String propName);

    /**
     * 新增商品规格
     *
     * @param prodSpecAddOrUpdateParam
     * @return
     */
    Integer saveProdSpec(ProdSpecAddOrUpdateParam prodSpecAddOrUpdateParam);


    /**
     * 修改商品规格
     *
     * @param prodSpecAddOrUpdateParam
     * @return
     */
    Integer modifyProdSpec(ProdSpecAddOrUpdateParam prodSpecAddOrUpdateParam);

    /**
     * 删除商品规格
     *
     * @param propId
     * @return
     */
    Integer removeProdSpec(Long propId);
}
