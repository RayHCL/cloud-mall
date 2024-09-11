package com.ray.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.domain.ProdTag;
import com.ray.mapper.ProdTagMapper;
import com.ray.service.ProdTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
*/
@Service
public class ProdTagServiceImpl extends ServiceImpl<ProdTagMapper, ProdTag> implements ProdTagService{
    @Autowired
    private ProdTagMapper prodTagMapper;

    @Override
    public boolean save(ProdTag prodTag) {
        prodTag.setCreateTime(new Date());
        prodTag.setUpdateTime(new Date());
        return prodTagMapper.insert(prodTag)>0;
    }

    @Override
    public boolean updateById(ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        return prodTagMapper.updateById(prodTag)>0;
    }
}
