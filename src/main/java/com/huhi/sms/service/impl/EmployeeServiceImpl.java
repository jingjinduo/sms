package com.huhi.sms.service.impl;

import com.huhi.sms.entity.Employee;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee selectByEmployeeId(String employeeId) {
        return employeeMapper.selectByEmployeeId(employeeId);
    }

    @Override
    public Employee checkEmployee(String loginId, String password) {
        Employee employee = employeeMapper.selectByLoginIdAndPassword(loginId, password);
        return employee;
    }
}
