package com.huhi.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huhi.sms.dao.SalaryMapper;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Salary;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    SalaryMapper salaryMapper;
    //查询工资
    public ResponseMessage historicalSalary(){
        //根据工号查询id
        //根据id查询工资
        return new ResponseMessage();
    }
    @ApiOperation(value = "查询所有")
    @PostMapping("/all")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage listAll(@RequestBody Employee employee) throws Exception {
        System.out.println(employee);
        return new ResponseMessage("200","成功",true
                ,salaryMapper.selectList(new QueryWrapper<Salary>().eq("employee_id",employee.getEmployeeId())));
    }

}
