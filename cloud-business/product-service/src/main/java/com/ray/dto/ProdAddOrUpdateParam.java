package com.ray.dto;

import com.ray.domain.Prod;
import com.ray.domain.Sku;
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
@ApiModel("新增或修改商品参数对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdAddOrUpdateParam extends Prod {

    @ApiModelProperty("商品分组标签id集合")
    private List<Long> tagList;

    @ApiModelProperty("商品sku对象集合")
    private List<Sku> skuList;

    @ApiModelProperty("配送方式对象")
    private DeliveryModeVo deliveryModeVo;
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