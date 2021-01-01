package com.huhi.sms.service.impl;

import com.huhi.sms.entity.Employee;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
