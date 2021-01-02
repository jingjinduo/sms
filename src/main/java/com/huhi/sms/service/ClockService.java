package com.huhi.sms.service;

import com.huhi.sms.entity.Clock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
public interface ClockService extends IService<Clock> {

    Boolean isContains(String employeeId, Integer clockStatus);
}
