package com.huhi.sms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Salary;
import com.huhi.sms.service.EmployeeService;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "|employee|")
public class EmployeeController {

    @Autowired(required=false)
    private EmployeeService employeeService;

    //注册
    @ApiOperation("员工注册")
    @PostMapping("/empSignUp")
    public ResponseMessage empSignUp(@RequestBody Employee employee) {
        Employee emp = employeeService.findByLoginIdAndPasswordAndEmployeeId(employee.getLoginId(), employee.getPassword(), employee.getEmployeeId());
        //如果都不重复
        if(null == emp){
            //先清空，才能自动生成Id,初始化员工注册时不能修改的项
            employee.setId(null);
            employee.setSalary(new BigDecimal(0));
            employeeService.insertEmp(employee);
            Map<String, Object> map = new HashMap<>();
            Employee employee1 = employeeService.checkEmployee(employee.getLoginId(), employee.getPassword());
            map.put("employeeId", employee1.getId());
            JSONObject json = new JSONObject(map);
            return new ResponseMessage("200","注册成功",true, json);
        }
        return new ResponseMessage("201","注册失败", false, null);

    }

    //登录
    @ApiOperation(value = "员工登录")
    @PostMapping("/empLogin")
    public ResponseMessage empLogin(@RequestBody Employee employee) {
        Employee emp = employeeService.checkEmployee(employee.getLoginId(), employee.getPassword());
        if (null != emp) {
            //相当于可以查到用户，进入用户中心页面,最好直接将密码设为Null,防止别人拿到
            emp.setPassword(null);
            Map<String, Object> map = new HashMap<>();
            map.put("employeeId", emp.getId());
            JSONObject json = new JSONObject(map);
            return new ResponseMessage("200","登陆成功",true, json);
        }
        //查不到用户，重定向回登陆页面
        return new ResponseMessage("201","登陆失败", false, null);
    }

    //注销
    @ApiOperation(value = "员工注销")
    @PostMapping("/empLogout")
    public ResponseMessage empLogout(@RequestBody Employee employee) {
        //如果有登录才能注销
        if(null != employee){
            JSONObject jsonObject = new JSONObject();
            Map<String, Object> map = new HashMap<>();
            map.put("loginId", null);
            return new ResponseMessage("200","注销成功",true, jsonObject);
        }
        return new ResponseMessage("201","注销失败", false, null);
    }

    //根据自己id修改自己的个人信息
    @ApiOperation(value = "员工信息修改")
    @PostMapping("/empUpdateInformation")
    public ResponseMessage empUpdateInformation(@RequestBody Employee employee) {
        //如果已经登陆，才能修改
        if(null != employee){
            boolean b = employeeService.update(employee, new UpdateWrapper<Employee>()
                    .eq("id", employee.getId())
                    .set("login_id",employee.getLoginId())
                    .set("password",employee.getPassword())
                    .set("employee_id",employee.getEmployeeId())
                    .set("sex",employee.getSex())
                    .set("picture",employee.getPicture())
                    .set("phone_num",employee.getPhoneNum())
                    .set("age",employee.getAge())
                    .set("name",employee.getName()));
            //如果修改成功,返回各种信息
            if(true == b){
                Map<String, Object> map = new HashMap<>();
                map.put("employeeId", employee.getId());
                JSONObject json = new JSONObject(map);
                return new ResponseMessage("200","修改成功",true, json);
            }

        }
        return new ResponseMessage("201","修改失败", false, null);
    }

    //根据自己id查找历史工资
    @ApiOperation(value = "员工查看历史工资")
    @PostMapping("/historicalSalary")
    public ResponseMessage historicalSalary(@RequestParam("employee_id") Long employeeId,
                                            @RequestParam("currentPage") Integer currentPage,
                                            @RequestParam("size") Integer size) {
        Page<Salary> empSalaryPage = new Page<>(currentPage, size);
        Page<Salary> salaryPage = employeeService.selectByIdForSalary(employeeId, empSalaryPage);
        if(salaryPage != null){
            return new ResponseMessage("200","查询成功",true, salaryPage);
        }
        return new ResponseMessage("201","查询失败", false, null);
    }




}
