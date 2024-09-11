package com.ray.service;

import com.ray.domain.ProdComm;
import com.baomidou.mybatisplus.extension.service.IService;
    /** 
 * @author Ray
 * @description
 * @date 2024/09/10
*/
public interface ProdCommService extends IService<ProdComm>{

        /**
         * 审核或回复商品评论
         * @param prodComm
         * @return
         */
        Integer examineOrReplyProdComm(ProdComm prodComm);
}
