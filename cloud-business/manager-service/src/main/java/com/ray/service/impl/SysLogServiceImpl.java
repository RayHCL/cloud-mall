package com.ray.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ray.domain.SysLog;
import com.ray.mapper.SysLogMapper;
import com.ray.service.SysLogService;
/** 
 * @author Ray
 * @description
 * @date 2024/08/11
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService{

}
