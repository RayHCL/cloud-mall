package com.ray.controller;

import com.ray.domain.Category;
import com.ray.model.Result;
import com.ray.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
@Api(tags = "商品类目管理")
@RequestMapping("prod/category")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    @ApiOperation("查询商品所有类目")
    @GetMapping("table")
    @PreAuthorize("hasAuthority('prod:category:page')")
    public Result<List<Category>> loadCategoryList() {
        List<Category> categories = categoryService.queryAllCategoryList();
        return Result.success(categories);
    }

    @ApiOperation("查询商品一级类目")
    @GetMapping("listCategory")
    @PreAuthorize("hasAuthority('prod:category:page')")
    public Result<List<Category>> loadRootCategoryList() {
        List<Category> rootCategoryList = categoryService.queryRootCategoryList();
        return Result.success(rootCategoryList);
    }

    @ApiOperation("新增商品类目")
    @PostMapping
    @PreAuthorize("hasAuthority('prod:category:save')")
    public Result<String> saveCategory(@RequestBody Category category) {
        Integer count = categoryService.addCategory(category);
        return Result.handle(count>0);
    }

    @ApiOperation("查询商品类目详情")
    @GetMapping("info/{categoryId}")
    @PreAuthorize("hasAuthority('prod:category:info')")
    public Result<Category> loadCategoryInfo(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return Result.success(category);
    }

    @ApiOperation("修改商品类目")
    @PutMapping
    @PreAuthorize("hasAuthority('prod:category:update')")
    public Result<String> modifyCategory(@RequestBody Category category) {
        Integer count = categoryService.modifyCategory(category);
        return Result.handle(count>0);
    }

    @ApiOperation("删除商品类目")
    @DeleteMapping("{categoryId}")
    @PreAuthorize("hasAuthority('prod:category:delete')")
    public Result<String> removeCategory(@PathVariable Long categoryId) {
        Integer count = categoryService.removeCategoryById(categoryId);
        return Result.handle(count>0);
    }
}
