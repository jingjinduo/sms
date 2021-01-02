package com.huhi.sms.dao;

import com.huhi.sms.entity.Clock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
public interface ClockMapper extends BaseMapper<Clock> {

    @Select("select count(*) from clock where employee_id = ${employeeId} and clock_status = ${clockStatus}")
    int isContains(String employeeId, Integer clockStatus);

}
