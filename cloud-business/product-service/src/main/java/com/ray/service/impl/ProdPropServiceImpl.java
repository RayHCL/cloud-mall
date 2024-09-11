package com.ray.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.domain.ProdProp;
import com.ray.domain.ProdPropValue;
import com.ray.dto.ProdSpecAddOrUpdateParam;
import com.ray.mapper.ProdPropMapper;
import com.ray.mapper.ProdPropValueMapper;
import com.ray.service.ProdPropService;
import com.ray.service.ProdPropValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
/** 
 * @author Ray
 * @description
 * @date 2024/09/10
*/
@Service
public class ProdPropServiceImpl extends ServiceImpl<ProdPropMapper, ProdProp> implements ProdPropService{
    @Resource
    private ProdPropMapper prodPropMapper;

    @Resource
    private ProdPropValueMapper prodPropValueMapper;
    @Resource
    private ProdPropValueService prodPropValueService;

    @Override
    public Page<ProdProp> queryProdSpecPage(Page<ProdProp> page, String propName) {
        // 多条件分页查询商品规格
        page = prodPropMapper.selectPage(page,new LambdaQueryWrapper<ProdProp>()
                .like(StringUtils.hasText(propName),ProdProp::getPropName,propName)
        );
        // 获取商品属性记录
        List<ProdProp> prodPropList = page.getRecords();
        // 判断是否有值
        if (CollectionUtils.isEmpty(prodPropList) || prodPropList.size() == 0) {
            return page;
        }
        // 从商品属性记录集合中获取商品属性id集合
        List<Long> propIdList = prodPropList.stream().map(ProdProp::getPropId).collect(Collectors.toList());
        // 根据商品属性id集合查询商品属性值集合
        List<ProdPropValue> prodPropValues = prodPropValueMapper.selectList(new LambdaQueryWrapper<ProdPropValue>()
                .in(ProdPropValue::getPropId, propIdList)
        );
        // 循环遍历商品属性记录
        prodPropList.forEach(prodProp -> {
            // 从商品属性值集合中过滤出，商品属性值对象的属性id与当前商品属性对象的属性id一致的属性值对象集合
            List<ProdPropValue> propValues = prodPropValues.stream()
                    .filter(prodPropValue -> prodPropValue.getPropId().equals(prodProp.getPropId()))
                    .collect(Collectors.toList());
            prodProp.setProdPropValues(propValues);
        });
        return page;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveProdSpec(ProdSpecAddOrUpdateParam prodSpecAddOrUpdateParam) {
        // 新增商品属性
        prodSpecAddOrUpdateParam.setShopId(1L);
        prodSpecAddOrUpdateParam.setRule(1);
        int i = prodPropMapper.insert(prodSpecAddOrUpdateParam);
        if (i > 0) {
            // 获取属性id
            Long propId = prodSpecAddOrUpdateParam.getPropId();
            // 获取商品属性值集合
            List<ProdPropValue> prodPropValues = prodSpecAddOrUpdateParam.getProdPropValues();
            // 循环遍历属性值集合，补充属性id值
            prodPropValues.forEach(prodPropValue -> prodPropValue.setPropId(propId));
            // 批量添加商品属性值
            prodPropValueService.saveBatch(prodPropValues);
        }
        return i;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer modifyProdSpec(ProdSpecAddOrUpdateParam prodSpecAddOrUpdateParam) {
        // 获取商品属性id
        Long propId = prodSpecAddOrUpdateParam.getPropId();
        // 删除商品规格原有的值
        prodPropValueMapper.delete(new LambdaQueryWrapper<ProdPropValue>()
                .eq(ProdPropValue::getPropId,propId)
        );
        // 获取商品属性值集合
        List<ProdPropValue> prodPropValues = prodSpecAddOrUpdateParam.getProdPropValues();
        // 循环遍历属性值集合，补充属性id值
        prodPropValues.forEach(prodPropValue -> prodPropValue.setPropId(propId));
        // 批量添加商品属性值
        prodPropValueService.saveBatch(prodPropValues);
        // 修改商品属性
        return prodPropMapper.updateById(prodSpecAddOrUpdateParam);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer removeProdSpec(Long propId) {
        // 根据商品属性标识删除商品属性值
        prodPropValueMapper.delete(new LambdaQueryWrapper<ProdPropValue>()
                .eq(ProdPropValue::getPropId,propId)
        );
        // 根据商品属性标识删除商品属性
        return prodPropMapper.deleteById(propId);
    }


}
