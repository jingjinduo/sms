package com.huhi.sms.controller;


import com.huhi.sms.entity.Employee;
import com.huhi.sms.service.EmployeeService;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

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

    @Resource
    private EmployeeService employeeService;

    //注册
    public ResponseMessage empSignUp() {
        return new ResponseMessage();
    }

    //登录
    @PostMapping("/empLogin")
    public ResponseMessage empLogin(@RequestParam String loginId, @RequestParam String password) {
        Employee employee = employeeService.checkEmployee(loginId, password);
        ResponseMessage responseMessage = new ResponseMessage();
        if(null != employee){
            //相当于可以查到用户，进入用户中心页面,最好直接将密码设为Null,防止别人拿到
            employee.setPassword(null);
            responseMessage.setMessage("登陆成功");
            responseMessage.setRespCode("200");
            responseMessage.setOk(true);
            return responseMessage;
        }
        //查不到用户，重定向回登陆页面
        responseMessage.setMessage("登陆失败");
        responseMessage.setRespCode("200");
        responseMessage.setOk(false);
        return responseMessage;
    }
    //注销
    public ResponseMessage empLogout() {
        return new ResponseMessage();
    }

    //根据自己id修改自己的个人信息
    public ResponseMessage empUpdateInformation() {

        return new ResponseMessage();
    }

    //根据自己id查找历史工资
    public ResponseMessage historicalSalary() {

        return new ResponseMessage();
    }


}
