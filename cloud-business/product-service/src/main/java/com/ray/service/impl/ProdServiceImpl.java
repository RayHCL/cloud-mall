package com.ray.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.domain.Prod;
import com.ray.domain.ProdTagReference;
import com.ray.domain.Sku;
import com.ray.dto.ProdAddOrUpdateParam;
import com.ray.mapper.ProdMapper;
import com.ray.mapper.ProdTagReferenceMapper;
import com.ray.mapper.SkuMapper;
import com.ray.service.ProdService;
import com.ray.service.ProdTagReferenceService;
import com.ray.service.SkuService;
import com.ray.vo.ProdDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
*/
@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService{
    @Resource
    private ProdMapper prodMapper;

    @Resource
    private ProdTagReferenceService prodTagReferenceService;

    @Resource
    private SkuService skuService;

    @Resource
    private ProdTagReferenceMapper prodTagReferenceMapper;

    @Resource
    private SkuMapper skuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addProd(ProdAddOrUpdateParam prodAddOrUpdateParam) {
        // 新增商品
        prodAddOrUpdateParam.setShopId(1L);
        prodAddOrUpdateParam.setSoldNum(0);
        // 获取配置送方式
        ProdAddOrUpdateParam.DeliveryModeVo deliveryModeVo = prodAddOrUpdateParam.getDeliveryModeVo();
        prodAddOrUpdateParam.setDeliveryMode(JSON.toJSONString(deliveryModeVo));
        prodAddOrUpdateParam.setCreateTime(new Date());
        prodAddOrUpdateParam.setUpdateTime(new Date());
        prodAddOrUpdateParam.setPutawayTime(new Date());
        prodAddOrUpdateParam.setVersion(0);
        int i = prodMapper.insert(prodAddOrUpdateParam);
        if (i > 0) {
            // 获取商品id
            Long prodId = prodAddOrUpdateParam.getProdId();
            // 获取商品分组标签id集合
            List<Long> tagIdList = prodAddOrUpdateParam.getTagList();
            // 判断是否有值
            if (!CollectionUtils.isEmpty(tagIdList) && tagIdList.size() != 0) {
                // 创建商品与分组标签关系集合对象
                List<ProdTagReference> prodTagReferences = new ArrayList<>();
                // 循环遍历商品分组标签id集合
                tagIdList.forEach(tagId -> {
                    // 创建商品与分组标签关系对象
                    ProdTagReference prodTagReference = new ProdTagReference();
                    prodTagReference.setProdId(prodId);
                    prodTagReference.setTagId(tagId);
                    prodTagReference.setShopId(1L);
                    prodTagReference.setCreateTime(new Date());
                    prodTagReference.setStatus(1);
                    // 将商品与分组标签关系对象添加到集合中
                    prodTagReferences.add(prodTagReference);
                });
                // 批量添加商品与分组标签关系集合
                prodTagReferenceService.saveBatch(prodTagReferences);
            }

            // 获取商品sku对象集合
            List<Sku> skuList = prodAddOrUpdateParam.getSkuList();
            // 判断是否有值
            if (!CollectionUtils.isEmpty(skuList) && skuList.size() != 0) {
                // 循环遍历商品sku对象集合
                skuList.forEach(sku -> {
                    sku.setProdId(prodId);
                    sku.setActualStocks(sku.getStocks());
                    sku.setCreateTime(new Date());
                    sku.setUpdateTime(new Date());
                    sku.setVersion(0);
                });
                // 批量添加商品sku对象
                skuService.saveBatch(skuList);
            }
        }
        return i;
    }




    @Override
    public ProdDetailVo queryProdDetailById(Long prodId) {
        ProdDetailVo prodDetailVo = new ProdDetailVo();
        // 根据标识查询商品信息
        Prod prod = prodMapper.selectById(prodId);
        // 将商品属性值copy到prodDetailVo对象的属性上
        BeanUtils.copyProperties(prod,prodDetailVo);
        // 根据商品标识查询商品与分组标签关系集合
        List<ProdTagReference> prodTagReferenceList = prodTagReferenceMapper.selectList(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodId)
        );
        // 判断是否有值
        if (!CollectionUtils.isEmpty(prodTagReferenceList) && prodTagReferenceList.size() != 0) {
            // 从商品与分组标签关系集合中获取分组标签id集合
            List<Long> tagIdList = prodTagReferenceList.stream().map(ProdTagReference::getTagId).collect(Collectors.toList());
            prodDetailVo.setTagList(tagIdList);
        }
        // 根据商品标识查询商品sku对象集合
        List<Sku> skuList = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId)
        );
        if (!CollectionUtils.isEmpty(skuList) && skuList.size() != 0) {
            skuList.forEach(sku -> sku.setStocks(sku.getActualStocks()));
            prodDetailVo.setSkuList(skuList);
        }
        return prodDetailVo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer modifyProd(ProdAddOrUpdateParam prodAddOrUpdateParam) {
        // 获取商品标识
        Long prodId = prodAddOrUpdateParam.getProdId();
        // 删除原有的商品与分组标签关系
        prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId,prodId)
        );
        // 获取商品分组标签id集合
        List<Long> tagIdList = prodAddOrUpdateParam.getTagList();
        // 判断是否有值
        if (!CollectionUtils.isEmpty(tagIdList) && tagIdList.size() != 0) {
            // 创建商品与分组标签关系集合对象
            List<ProdTagReference> prodTagReferences = new ArrayList<>();
            // 循环遍历商品分组标签id集合
            tagIdList.forEach(tagId -> {
                // 创建商品与分组标签关系对象
                ProdTagReference prodTagReference = new ProdTagReference();
                prodTagReference.setProdId(prodId);
                prodTagReference.setTagId(tagId);
                prodTagReference.setShopId(1L);
                prodTagReference.setCreateTime(new Date());
                prodTagReference.setStatus(1);
                // 将商品与分组标签关系对象添加到集合中
                prodTagReferences.add(prodTagReference);
            });
            // 批量添加商品与分组标签关系集合
            prodTagReferenceService.saveBatch(prodTagReferences);
        }
        // 删除原有商品sku对象
        skuMapper.delete(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId,prodId)
        );
        // 获取商品sku对象集合
        List<Sku> skuList = prodAddOrUpdateParam.getSkuList();
        // 判断是否有值
        if (!CollectionUtils.isEmpty(skuList) && skuList.size() != 0) {
            // 循环遍历商品sku对象集合
            skuList.forEach(sku -> {
                sku.setProdId(prodId);
                sku.setActualStocks(sku.getStocks());
                sku.setCreateTime(new Date());
                sku.setUpdateTime(new Date());
                sku.setVersion(0);
            });
            // 批量添加商品sku对象
            skuService.saveBatch(skuList);
        }
        // 更新商品信息
        prodAddOrUpdateParam.setUpdateTime(new Date());
        return prodMapper.updateById(prodAddOrUpdateParam);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer removeProdsByIds(List<Long> prodIds) {
        // 标识商品id集合批量删除商品与分组标签关系
        prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .in(ProdTagReference::getProdId,prodIds)
        );
        // 根据商品id集合批量删除商品sku对象集合
        skuMapper.delete(new LambdaQueryWrapper<Sku>()
                .in(Sku::getProdId,prodIds)
        );
        return prodMapper.deleteBatchIds(prodIds);
    }
}
