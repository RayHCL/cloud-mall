package com.ray.vo;

import com.ray.domain.Prod;
import com.ray.domain.Sku;
import com.ray.dto.ProdAddOrUpdateParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
@ApiModel("商品详情信息对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdDetailVo extends Prod {
    @ApiModelProperty("商品分组标签id集合")
    private List<Long> tagList;

    @ApiModelProperty("商品sku对象集合")
    private List<Sku> skuList;

    @ApiModelProperty("配送方式对象")
    private ProdAddOrUpdateParam.DeliveryModeVo deliveryModeVo;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class DeliveryModeVo {
        @ApiModelProperty("商品配送")
        private Boolean hasShopDelivery;
        @ApiModelProperty("用户自提")
        private Boolean hasUserPickUp;
    }
}