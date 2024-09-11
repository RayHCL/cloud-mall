package com.ray.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.domain.Prod;
import com.ray.domain.ProdProp;
import com.ray.domain.ProdPropValue;
import com.ray.domain.ProdTag;
import com.ray.dto.ProdAddOrUpdateParam;
import com.ray.model.Result;
import com.ray.service.ProdPropService;
import com.ray.service.ProdPropValueService;
import com.ray.service.ProdService;
import com.ray.service.ProdTagService;
import com.ray.vo.ProdDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "商品接口管理")
@RequestMapping("prod/prod")
@RestController
public class ProdController {

    @Resource
    private ProdService prodService;
    @Resource
    private ProdTagService prodTagService;
    @Resource
    private ProdPropService prodPropService;
    @Resource
    private ProdPropValueService prodPropValueService;

    @ApiOperation("多条件分页查询商品列表")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:prod:page')")
    public Result<Page<Prod>> loadProdPage(Page<Prod> page, Prod prod) {
        page = prodService.page(page,new LambdaQueryWrapper<Prod>()
                .eq(ObjectUtil.isNotNull(prod.getStatus()),Prod::getStatus,prod.getStatus())
                .like(StringUtils.hasText(prod.getProdName()),Prod::getProdName,prod.getProdName())
                .orderByDesc(Prod::getCreateTime)
        );
        return Result.success(page);
    }


    @ApiOperation("查询商品分组标签集合")
    @GetMapping("listTagList")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public Result<List<ProdTag>> loadProdTagList() {
        List<ProdTag> list = prodTagService.list(new LambdaQueryWrapper<ProdTag>()
                .eq(ProdTag::getStatus, 1)
                .orderByDesc(ProdTag::getSeq)
        );
        return Result.success(list);
    }

    @ApiOperation("查询商品属性列表")
    @GetMapping("list")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public Result<List<ProdProp>> loadProdPropList() {
        List<ProdProp> list = prodPropService.list(new LambdaQueryWrapper<ProdProp>()
                .orderByDesc(ProdProp::getPropId)
        );
        return Result.success(list);
    }

    @ApiOperation("根据商品属性标识查询属性值集合")
    @GetMapping("listSpecValue/{propId}")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public Result<List<ProdPropValue>> loadProdPropValues(@PathVariable Long propId) {
        List<ProdPropValue> list = prodPropValueService.list(new LambdaQueryWrapper<ProdPropValue>()
                .eq(ProdPropValue::getPropId, propId)
        );
        return Result.success(list);
    }


    @ApiOperation("新增商品")
    @PostMapping
    @PreAuthorize("hasAuthority('prod:prod:save')")
    public Result<String> saveProd(@RequestBody ProdAddOrUpdateParam prodAddOrUpdateParam) {
        Integer count = prodService.addProd(prodAddOrUpdateParam);
        return Result.handle(count>0);
    }

    @ApiOperation("根据标识查询商品详情")
    @GetMapping("info/{prodId}")
    @PreAuthorize("hasAuthority('prod:prod:info')")
    public Result<ProdDetailVo> loadProdDetail(@PathVariable Long prodId) {
        ProdDetailVo prodDetailVo = prodService.queryProdDetailById(prodId);
        return Result.success(prodDetailVo);
    }


    @ApiOperation("修改商品信息")
    @PutMapping
    @PreAuthorize("hasAuthority('prod:prod:update')")
    public Result<String> modifyProd(@RequestBody ProdAddOrUpdateParam prodAddOrUpdateParam) {
        Integer count = prodService.modifyProd(prodAddOrUpdateParam);
        return Result.handle(count>0);
    }

    @ApiOperation("批量删除商品")
    @DeleteMapping("{prodIds}")
    @PreAuthorize("hasAuthority('prod:prod:delete')")
    public Result<String> removeProds(@PathVariable List<Long> prodIds) {
        Integer count = prodService.removeProdsByIds(prodIds);
        return Result.handle(count==prodIds.size());
    }
}