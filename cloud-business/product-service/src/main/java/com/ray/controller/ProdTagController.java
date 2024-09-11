package com.ray.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.domain.ProdTag;
import com.ray.model.Result;
import com.ray.service.ProdTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
@Api(tags = "商品分组标签接口管理")
@RequestMapping("prod/prodTag")
@RestController
public class ProdTagController {

    @Resource
    private ProdTagService prodTagService;

    @ApiOperation("多条件分页查询分组标签")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:prodTag:page')")
    public Result<Page<ProdTag>> loadProdTagPage(Page<ProdTag> page, ProdTag prodTag) {
        page = prodTagService.page(page,new LambdaQueryWrapper<ProdTag>()
                .eq(ObjectUtil.isNotNull(prodTag.getStatus()),ProdTag::getStatus,prodTag.getStatus())
                .like(StringUtils.hasText(prodTag.getTitle()),ProdTag::getTitle,prodTag.getTitle())
                .orderByDesc(ProdTag::getSeq)
        );
        return Result.success(page);
    }

    @ApiOperation("新增商品分组标签")
    @PostMapping
    @PreAuthorize("hasAuthority('prod:prodTag:save')")
    public Result<String> saveProdTag(@RequestBody ProdTag prodTag) {
        boolean flag = prodTagService.save(prodTag);
        return Result.handle(flag);
    }

    @ApiOperation("根据标识查询商品分组标签详情")
    @GetMapping("info/{tagId}")
    @PreAuthorize("hasAuthority('prod:prodTag:info')")
    public Result<ProdTag> loadProdTagInfo(@PathVariable Long tagId) {
        ProdTag prodTag = prodTagService.getById(tagId);
        return Result.success(prodTag);
    }

    @ApiOperation("修改商品分组标签信息")
    @PutMapping
    @PreAuthorize("hasAuthority('prod:prodTag:update')")
    public Result<String> modifyProdTag(@RequestBody ProdTag prodTag) {
        boolean flag = prodTagService.updateById(prodTag);
        return Result.handle(flag);
    }

    @ApiOperation("删除商品分组标签")
    @DeleteMapping("{tagId}")
    @PreAuthorize("hasAuthority('prod:prodTag:delete')")
    public Result<String> removeProdTag(@PathVariable Long tagId) {
        boolean flag = prodTagService.removeById(tagId);
        return Result.handle(flag);
    }
}
