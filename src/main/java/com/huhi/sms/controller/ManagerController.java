package com.huhi.sms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Manager;
import com.huhi.sms.service.EmployeeService;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
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

    @Autowired(required=false)
    private EmployeeService employeeService;

    @Autowired(required=false)
    private Manager manager;

    //管理员登录,判断是高级管理员还是普通管理员
    public ResponseMessage magLogin(){
        return new ResponseMessage();
    }

    //管理员退出
    public ResponseMessage magLogout(){

        return new ResponseMessage();
    }

    //添加用户账户
    public ResponseMessage magAddEmp(){
        return new ResponseMessage();
    }

    //添加管理员
    public ResponseMessage magAddMag(){
        //首先判断是否为高级管理员
        //高级管理员可以继续操作
        //非高级管理员则返回相应提示信息
        return new ResponseMessage();
    }

    //更新用户
    public ResponseMessage updateEmp(){
        return new ResponseMessage();
    }

    //更新管理员
    public ResponseMessage updateMag(){
        //首先判断是否为高级管理员
        //高级管理员可以继续操作
        //非高级管理员则返回相应提示信息
        return new ResponseMessage();
    }

    //删除用户
    public ResponseMessage deleteEmp(){

        return new ResponseMessage();
    }

    //删除用户
    public ResponseMessage deleteMag(){

        return new ResponseMessage();
    }

    //查找，通过id查找员工或管理员信息
    public ResponseMessage selectById(){
        return new ResponseMessage();
    }


    //查询所有用户
    @ApiOperation(value = "|Manager|", notes = "查询所有用户")
    @RequestMapping("/findAllEmp")
    public ResponseMessage findAll(@RequestParam Integer currentPage,
                                   @RequestParam Integer pageSize){
        Page<Employee> employeePage = new Page<>(currentPage, pageSize);
        return new ResponseMessage("200","查询成功",true,employeeService.findAllEmp(employeePage));
    }

}
