package com.huhi.sms.dao;

import com.huhi.sms.entity.Employee;
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
public interface EmployeeMapper extends BaseMapper<Employee> {



    @Select("select * from employee where login_id = ${loginId} and password = ${password}")
    Employee selectByLoginIdAndPassword(String loginId, String password);
}
