package com.huhi.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.dao.SalaryMapper;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.entity.Salary;
import com.huhi.sms.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired(required=false)
    private EmployeeMapper employeeMapper;

    @Autowired(required=false)
    private SalaryMapper salaryMapper;
    @Override
    public Employee selectByEmployeeId(String employeeId) {
        return employeeMapper.selectOne( new QueryWrapper<Employee>()
                .eq("employee_id", employeeId));
    }


    //按登录名和密码查询数据
    @Override
    public Employee checkEmployee(String loginId, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("login_id",loginId);
        map.put("password", password);
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().allEq(map));
        return employee;
    }

    @Override
    public Employee findByLoginIdAndPasswordAndEmployeeId(String loginId, String password, String employeeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("login_id",loginId);
        map.put("password", password);
        map.put("employee_id", employeeId);
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().allEq(map));
        return employee;
    }

    @Override
    public Page<Employee> findAllEmp(Page<Employee> page) {
        return employeeMapper.selectPage(page, null);
    }

    @Override
    public Boolean insertEmp(Employee employee) {
        employeeMapper.insert(employee);
        return true;
    }

    @Override
    public Boolean updateInLoginId(Employee employee) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("login_id", employee.getLoginId());
        employeeMapper.update(employee, updateWrapper);
        return null;
    }

    @Override
    public Page<Salary> selectByIdForSalary(Long employeeId, Page<Salary> page) {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id", employeeId);
        return salaryMapper.selectPage(page, queryWrapper);
    }

}
