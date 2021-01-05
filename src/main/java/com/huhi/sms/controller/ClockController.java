package com.huhi.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.text.SimpleDateFormat;
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

    @ApiOperation(value = "当日是否打卡")
    @PostMapping("/check")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage check(@RequestParam("employeeId")String eid,@RequestParam("day")String day) throws Exception {
        System.out.println(eid+":"+day);
        if(day.length()>10){
            day=day.substring(0,10);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date dayStart = sdf.parse(day+" 00:00:00");
        Date dayEnd = sdf.parse(day+" 23:59:59");
        QueryWrapper<Clock>queryWrapper=new QueryWrapper<>();
        queryWrapper.between("time",dayStart,dayEnd);
        queryWrapper.eq("employee_id",eid);
        if(clockMapper.selectCount(queryWrapper)==2)
            return new ResponseMessage("200","成功",true,null);
        else
            return new ResponseMessage("201","失败",false,null);
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
    @GetMapping("/clock")
    public ResponseMessage clockIn(){

        return new ResponseMessage();
    }
}
