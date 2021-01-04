package com.huhi.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huhi.sms.entity.Salary;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
public interface EmployeeService extends IService<Employee> {
    Employee selectByEmployeeId(String employeeId);
    //根据登录名和密码查询用户
    Employee checkEmployee(String loginId, String password);

    Employee findByLoginIdAndPasswordAndEmployeeId(String loginId, String password, String employeeId);

    //分页查询所有员工
    Page<Employee> findAllEmp(Page<Employee> page);
    //注册用户
    Boolean insertEmp(Employee employee);

    Boolean updateInLoginId(Employee employee);

    //根据id查工资表，分页返回
    Page<Salary> selectByIdForSalary(Long employeeId, Page<Salary> page);
}
