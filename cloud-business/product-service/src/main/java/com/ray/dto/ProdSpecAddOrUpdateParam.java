package com.ray.dto;

import com.ray.domain.ProdProp;
import com.ray.domain.ProdPropValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("新增或修改商品规格参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdSpecAddOrUpdateParam extends ProdProp {

    @ApiModelProperty("商品规格值集合")
    private List<ProdPropValue> prodPropValues;
}