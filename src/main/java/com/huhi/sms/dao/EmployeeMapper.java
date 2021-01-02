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

    //通过EmployeeId查找员工
    @Select("select * from employee where employee_id = ${employeeId}")
    Employee selectByEmployeeId(String employeeId);
}
