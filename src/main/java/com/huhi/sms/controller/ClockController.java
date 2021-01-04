package com.huhi.sms.controller;


import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.entity.Clock;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.service.ClockService;
import com.huhi.sms.service.EmployeeService;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
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
@RequestMapping("/clock")
@Api(tags = "|clock|")
public class ClockController {

    @Autowired(required=false)
    private ClockMapper clockMapper;

    @Autowired(required=false)
    private EmployeeService employeeService;

    @Autowired(required=false)
    private ClockService clockService;

    @Data
    public static class Requestvo{
        String query;
        int pagenum;
        String pagesize;
    }
    @ApiOperation(value = "查询所有")
    @PostMapping("/all")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage submitApproval(@RequestBody Requestvo vo) throws Exception {
        System.out.println(vo);
        return new ResponseMessage("200","成功",true,clockMapper.selectList(null));
    }

    //签到 or 签退
    //同一天不能签到/签退两次
    @GetMapping("/clock/{employeeId}")
    public ResponseMessage clockIn(@PathParam("employeeId") String employeeId,
                                   @PathParam("clockStatus") Integer clockStatus,
                                   @PathParam("time") Date date){
        //首先从前端返回的路径中获取employeeId，通过employeeId查询员工
        //这里没写完！！！！！！！！！！！！！！！！下面的不对
        Employee employee = employeeService.selectByEmployeeId(employeeId);
        //先判断是否已经签到/签退
        if(!clockService.isContains(employeeId, clockStatus)){
            //将查到的员工id传给clock对象
            Clock clock = new Clock();
            clock.setId(employee.getId());
            clock.setTime(new Date());
            clock.setClockStatus(clockStatus);
            clock.setEmployeeId(employeeId);
            clockMapper.insert(clock);
            return new ResponseMessage("打卡成功！");
        }

        return new ResponseMessage();
    }
}
