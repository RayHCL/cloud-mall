package com.ray.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.domain.ProdProp;
import com.ray.dto.ProdSpecAddOrUpdateParam;
import com.ray.model.Result;
import com.ray.service.ProdPropService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品规格接口管理")
@RequestMapping("prod/spec")
@RestController
public class ProdSpecController {

    @Autowired
    private ProdPropService prodPropService;

    @ApiOperation("多条件分页查询商品规格")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:spec:page')")
    public Result<Page<ProdProp>> loadProdSpecPage(Page<ProdProp> page, String propName) {
        page = prodPropService.queryProdSpecPage(page, propName);
        return Result.success(page);
    }

    @ApiOperation("新增商品规格")
    @PostMapping
    @PreAuthorize("hasAuthority('prod:spec:save')")
    public Result<String> saveProdSpec(@RequestBody ProdSpecAddOrUpdateParam prodSpecAddOrUpdateParam) {
        Integer count = prodPropService.saveProdSpec(prodSpecAddOrUpdateParam);
        return Result.handle(count>0);
    }

    @ApiOperation("修改商品规格")
    @PutMapping
    @PreAuthorize("hasAuthority('prod:spec:update')")
    public Result<String> modifyProdSpec(@RequestBody ProdSpecAddOrUpdateParam prodSpecAddOrUpdateParam) {
        Integer count = prodPropService.modifyProdSpec(prodSpecAddOrUpdateParam);
        return Result.handle(count>0);
    }


    @ApiOperation("删除商品规格")
    @DeleteMapping("{propId}")
    @PreAuthorize("hasAuthority('prod:spec:delete')")
    public Result<String> removeProdSpec(@PathVariable Long propId) {
        Integer count = prodPropService.removeProdSpec(propId);
        return Result.handle(count>0);
    }
}