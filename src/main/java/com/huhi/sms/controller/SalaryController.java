package com.huhi.sms.controller;


import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/salary")
@Api(tags = "|salary|")
public class SalaryController {

    //查询工资
    public ResponseMessage historicalSalary(){
        //根据工号查询id
        //根据id查询工资
        return new ResponseMessage();
    }

}
