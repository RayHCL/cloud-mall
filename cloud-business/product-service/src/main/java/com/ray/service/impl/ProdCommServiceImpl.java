package com.ray.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.domain.ProdComm;
import com.ray.mapper.ProdCommMapper;
import com.ray.service.ProdCommService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
*/
@Service
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService{
    @Resource
    private ProdCommMapper prodCommMapper;

    @Override
    public Integer examineOrReplyProdComm(ProdComm prodComm) {
        // 获取回复内容
        String replyContent = prodComm.getReplyContent();
        // 判断是否有回复内容
        if (StringUtils.hasText(replyContent)) {
            // 设置回复时间
            prodComm.setReplyTime(new Date());
            // 设置回复状态
            prodComm.setReplySts(1);
        }
        return prodCommMapper.updateById(prodComm);
    }
}
