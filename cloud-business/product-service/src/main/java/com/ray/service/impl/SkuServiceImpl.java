package com.ray.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.mapper.SkuMapper;
import com.ray.domain.Sku;
import com.ray.service.SkuService;
/** 
 * @author Ray
 * @description
 * @date 2024/09/10
*/
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService{

}
