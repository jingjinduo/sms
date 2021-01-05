package com.huhi.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huhi.sms.dao.SalaryMapper;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Salary;
import com.huhi.sms.util.DateFormatUtil;
import com.huhi.sms.util.ResponseMessage;
import com.huhi.sms.vo.BetweenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    @Autowired(required = false)
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

    @ApiOperation(value = "查询区间")
    @PostMapping("/between")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage between(@RequestBody BetweenVO betweenVO) throws Exception {
        Date start=DateFormatUtil.string2Date(betweenVO.getStartDate());
        Date end=DateFormatUtil.string2Date(betweenVO.getEndDate());
        System.out.println(betweenVO.getStartDate());
        if(start.after(end)){
            return new ResponseMessage("201","时间前后冲突",false
                    ,null);
        }
        QueryWrapper<Salary>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("employee_id",betweenVO.getEmployeeId());
        queryWrapper.between("date",start,end);
        return new ResponseMessage("200","成功",true
                ,salaryMapper.selectList(queryWrapper));
    }

}
