package com.huhi.sms.service.impl;

import com.huhi.sms.entity.Clock;
import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.service.ClockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
@Service
public class ClockServiceImpl extends ServiceImpl<ClockMapper, Clock> implements ClockService {

    @Resource
    private ClockMapper clockMapper;

    @Override
    public Boolean isContains(String employeeId, Integer clockStatus) {
        //看可以查到几个已经打卡的信息
        int num = clockMapper.isContains(employeeId, clockStatus);
        boolean flag = false;
        //如果查到多于0条记录，就表示已经打过卡
        if(num > 0){
            flag = true;
        }
        return flag;
    }
}
