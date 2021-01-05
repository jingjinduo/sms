package com.huhi.sms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Manager;
import com.huhi.sms.service.EmployeeService;
import com.huhi.sms.service.ManagerService;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/manager")
@Api(tags = "|manager|")
public class ManagerController {

    @Autowired(required = false)
    private EmployeeService employeeService;

    @Autowired(required = false)
    private ManagerService managerService;

    @Autowired(required = false)
    private Manager manager;

    //管理员登录
    @ApiOperation(value = "管理员登录")
    @PostMapping("/magLogin")
    public ResponseMessage magLogin(@RequestBody Manager man) {
        Manager manager = managerService.checkManage(man.getLoginId(), man.getPassword());
        if (null != manager) {
            //相当于可以查到用户，进入用户中心页面,最好直接将密码设为Null,防止别人拿到
            manager.setPassword(null);
            Map<String, Object> map = new HashMap<>();
            map.put("managerId", manager.getId());
            map.put("is_admin", manager.getIsAdmin());
            JSONObject json = new JSONObject(map);
            return new ResponseMessage("200","登陆成功",true, json);
        }
        //查不到用户，重定向回登陆页面
        return new ResponseMessage("201","登陆失败", false, null);
    }


    //管理员退出
    @ApiOperation(value = "管理员退出")
    @PostMapping("/magLogout")
    public ResponseMessage magLogout(@RequestBody Manager manager) {
        //如果有登录才能注销
        if(null != manager){
            Map<String, Object> map = new HashMap<>();
            map.put("ManagerLoginId", null);
            JSONObject jsonObject = new JSONObject(map);
            return new ResponseMessage("200","注销成功",true, jsonObject);
        }
        return new ResponseMessage("201","注销失败", false, null);
    }

    //添加员工账户
    @ApiOperation(value = "管理员添加员工")
    @PostMapping("/magAddEmp")
    public ResponseMessage magAddEmp(@RequestBody Employee employee) {
        Employee emp = employeeService.findByLoginIdAndPasswordAndEmployeeId(employee.getLoginId(), employee.getPassword(), employee.getEmployeeId());
        //如果都不重复
        if(null == emp){
            //先清空，才能自动生成Id,初始化员工注册时不能修改的项
            //管理员创建用户时可以修改工资
            employee.setId(null);
            employeeService.insertEmp(employee);
            Map<String, Object> map = new HashMap<>();
            Employee employee1 = employeeService.checkEmployee(employee.getLoginId(), employee.getPassword());
            map.put("employeeId", employee1.getId());
            JSONObject json = new JSONObject(map);
            return new ResponseMessage("200","注册成功",true, json);
        }
        return new ResponseMessage("201","注册失败", false, null);
    }

    //添加管理员
    @ApiOperation(value = "高级管理员添加管理员")
    @PostMapping("/magAddMag")
    public ResponseMessage magAddMag(@RequestBody  Map<String, Manager> managersList) {
        //首先判断是否为高级管理员
        if(0 != managersList.get("currentManager").getIsAdmin()){
            //高级管理员可以继续操作
            managersList.get("manager").setId(null);
            Boolean b = managerService.insertManager(managersList.get("manager"));
            if(b)
                return new ResponseMessage("200","添加成功", true, null);
        }
        //非高级管理员则返回相应提示信息
        return new ResponseMessage("201", "添加失败", false, null);
    }

    //更新用户
    @ApiOperation(value = "管理员更新员工信息")
    @PostMapping("/updateEmp")
    public ResponseMessage updateEmp(@RequestBody Employee employee) {
        boolean b = employeeService.update(employee, new UpdateWrapper<Employee>().eq("id", employee.getId()));
        if(b) return new ResponseMessage("200", "更新成功", true, null);
        return new ResponseMessage("201", "更新失败", false, null);
    }

    //更新管理员
    @ApiOperation(value = "高级管理员更新管理员信息")
    @PostMapping("/updateMag")
    public ResponseMessage updateMag(@RequestBody  Map<String, Manager> managersList) {
        //首先判断是否为高级管理员
        if(0 != managersList.get("currentManager").getIsAdmin()){
            //高级管理员可以继续操作
            boolean b = managerService.update(managersList.get("manager"), new UpdateWrapper<Manager>().eq("id", managersList.get("manager").getId()));
            if(b) return new ResponseMessage("200", "更新成功", true, null);
        }

        //非高级管理员则返回相应提示信息
        return new ResponseMessage("201", "更新失败", false, null);
    }

    //删除用户
    @ApiOperation(value = "管理员软删除用户")
    @PostMapping("/deleteEmp")
    public ResponseMessage deleteEmp(@RequestParam String employeeId) {
        //根据id删除用户
        boolean b = employeeService.update(new UpdateWrapper<Employee>().eq("employee_id", employeeId).set("login_id", "*"));
        if(b)  return new ResponseMessage("200","删除成功", true, null);
        return new ResponseMessage("201", "删除失败", false, null);
    }

    //删除管理员
    @ApiOperation(value = "高级管理员软删除管理员")
    @PostMapping("/deleteMag")
    public ResponseMessage deleteMag(@RequestBody  Map<String, Manager> managersList) {

        //高级管理员才能删除管理员
        if(0 != managersList.get("currentManager").getIsAdmin()){
            managersList.get("manager").setLoginId("*");
            //高级管理员可以继续操作
            boolean b = managerService.update( managersList.get("manager"), new UpdateWrapper<Manager>().eq("id", managersList.get("manager").getId()));
            if(b) return new ResponseMessage("200", "更新成功", true, null);
        }
        return new ResponseMessage("201","更新失败", false, null);
    }


    //查找，通过工号查找员工信息
    @ApiOperation(value = "查找员工信息", notes = "通过工号查找员工信息")
    @PostMapping("/selectEmpById")
    public ResponseMessage selectEmpById(@RequestParam String employeeId) {
        Employee employee = employeeService.selectByEmployeeId(employeeId);
        if(null != employee) return new ResponseMessage("200", "查找成功", true, employee);
        return new ResponseMessage("200", "查找失败", false, null);
    }
    //通过loginId查找管理员信息
    @ApiOperation(value = "查找管理员信息", notes = "通过loginId查找管理员信息")
    @PostMapping("/selectManaById")
    public ResponseMessage selectManaById(@RequestParam String managerLoginId) {
        Manager manager = managerService.selectByManaId(managerLoginId);
        if(null != manager) return new ResponseMessage("200", "查找成功", true, manager);
            return new ResponseMessage("200", "查找失败", false, null);
    }


    //查询所有用户
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @PostMapping("/findAllEmp")
    public ResponseMessage findAllEmp(@RequestParam Integer currentPage,
                                      @RequestParam Integer pageSize) {
        Page<Employee> employeePage = new Page<>(currentPage, pageSize);
        Page<Employee> allEmp = employeeService.findAllEmp(employeePage);
        if(null != allEmp)
            return new ResponseMessage("200", "查询成功", true, allEmp);
        return new ResponseMessage("201", "查询失败", false, null);
    }

    //查询所有管理员
    @ApiOperation(value = "查询所有管理员", notes = "高级管理员可以查询所有管理员")
    @PostMapping("/findAllMana")
    public ResponseMessage findAllMana(@RequestParam Integer currentPage,
                                       @RequestParam Integer pageSize,
                                       @RequestBody Manager currentManager) {

        if(1 == currentManager.getIsAdmin()){

            Page<Manager> managerPage = new Page<>(currentPage, pageSize);

            Page<Manager> allManaByPage = managerService.selectAllManaByPage(managerPage);

            if(null != allManaByPage)
                return new ResponseMessage("200", "查询成功", true, allManaByPage);
        }
        return new ResponseMessage("201", "查询失败", false, null);
    }


}
