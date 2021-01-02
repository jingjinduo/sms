package com.huhi.sms.controller;


import com.huhi.sms.entity.Employee;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    //注册
    public ResponseMessage empSignUp() {
        return new ResponseMessage();
    }

    //登录
    @RequestMapping("/empLogin")
    @ResponseBody
    public ResponseMessage empLogin(@RequestBody Employee employee) {

        return new ResponseMessage();
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
