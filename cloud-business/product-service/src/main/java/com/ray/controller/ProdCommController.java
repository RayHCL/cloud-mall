package com.ray.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ray.domain.ProdComm;
import com.ray.model.Result;
import com.ray.service.ProdCommService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "评论接口管理")
@RequestMapping("prod/prodComm")
@RestController
public class ProdCommController {

    @Autowired
    private ProdCommService prodCommService;

    @ApiOperation("多条件分页查询评论列表")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('prod:prodComm:page')")
    public Result<Page<ProdComm>> loadProdCommPage(Page<ProdComm> page, ProdComm prodComm) {
        page = prodCommService.page(page,new LambdaQueryWrapper<ProdComm>()
                .eq(ObjectUtil.isNotNull(prodComm.getStatus()),ProdComm::getStatus,prodComm.getStatus())
                .like(StringUtils.hasText(prodComm.getProdName()),ProdComm::getProdName,prodComm.getProdName())
                .orderByDesc(ProdComm::getCreateTime)
        );
        return Result.success(page);
    }

    @ApiOperation("根据标识查询评论详情")
    @GetMapping("{commId}")
    @PreAuthorize("hasAuthority('prod:prodComm:info')")
    public Result<ProdComm> loadProdCommInfo(@PathVariable Long commId) {
        ProdComm prodComm = prodCommService.getById(commId);
        return Result.success(prodComm);
    }

    @ApiOperation("审核或回复商品评论")
    @PutMapping
    @PreAuthorize("hasAuthority('prod:prodComm:update')")
    public Result<String> modifyProdComm(@RequestBody ProdComm prodComm) {
        Integer count = prodCommService.examineOrReplyProdComm(prodComm);
        return Result.handle(count>0);
    }
}