package com.ray.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ray.domain.Prod;
import com.ray.dto.ProdAddOrUpdateParam;
import com.ray.vo.ProdDetailVo;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
*/
public interface ProdService extends IService<Prod>{

        /**
         * 新增商品
         * @param prodAddOrUpdateParam
         * @return
         */
        Integer addProd(ProdAddOrUpdateParam prodAddOrUpdateParam);


    /**
     * 根据标识查询商品详情
     * @param prodId
     * @return
     */
    ProdDetailVo queryProdDetailById(Long prodId);


    /**
     * 修改商品信息
     * @param prodAddOrUpdateParam
     * @return
     */
    Integer modifyProd(ProdAddOrUpdateParam prodAddOrUpdateParam);

    /**
     * 批量删除商品
     * @param prodIds
     * @return
     */
    Integer removeProdsByIds(List<Long> prodIds);
}
