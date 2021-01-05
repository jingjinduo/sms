package com.huhi.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.entity.Clock;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.service.ClockService;
import com.huhi.sms.service.EmployeeService;
import com.huhi.sms.util.ClockStatus;
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
import java.text.ParseException;
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

    @Autowired
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

    //签到 or 签退
    //同一天不能签到/签退两次
    @ApiOperation(value = "更新打卡按钮状态")
    @GetMapping("/update")
    public ResponseMessage update(@RequestParam("employeeId") String employeeId) throws ParseException {
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String day=formatter.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date dayStart = sdf.parse(day+" 00:00:00");
        Date dayEnd = sdf.parse(day+" 23:59:59");
        Clock now=clockMapper.selectOne(new QueryWrapper<Clock>()
                .between("time",dayStart,dayEnd)
                .eq("employee_id",employeeId));
        Clock tem=new Clock();
        tem.setEmployeeId(employeeId);
        tem.setTime(new Date());
        if(now==null){
            tem.setClockStatus(ClockStatus.DEFUALT.getStatenum());
            clockMapper.insert(tem);
            return new ResponseMessage("200","成功",true,ClockStatus.DEFUALT.getStatenum());
        }
        else {
            if(now.getClockStatus()==ClockStatus.SIGNIN.getStatenum()){
                return new ResponseMessage("200","成功",true,ClockStatus.SIGNIN.getStatenum());
            }
            else if(now.getClockStatus()==ClockStatus.COMPLETE.getStatenum()){
                return new ResponseMessage("200","成功",true,ClockStatus.COMPLETE.getStatenum());
            }
        }
        return new ResponseMessage("201","失败",true,null);
    }

    //签到 or 签退
    //同一天不能签到/签退两次
    @ApiOperation(value = "签到/签退")
    @GetMapping("")
    public ResponseMessage clockIn(@RequestParam("employeeId") String employeeId) throws ParseException {
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String day=formatter.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date dayStart = sdf.parse(day+" 00:00:00");
        Date dayEnd = sdf.parse(day+" 23:59:59");
/*
        Date signin=sdf.parse(day+" 8:0:0");
        Date signout=sdf.parse(day+" 17:0:0");*/
        Clock now=clockMapper.selectOne(new QueryWrapper<Clock>()
                .between("time",dayStart,dayEnd)
                .eq("employee_id",employeeId));

        if(now.getClockStatus()==ClockStatus.DEFUALT.getStatenum()){
            now.setClockStatus(ClockStatus.SIGNIN.getStatenum());
            clockMapper.updateById(now);
            return new ResponseMessage("200","成功",true,ClockStatus.SIGNIN.getStatenum());
        }
        else if(now.getClockStatus()==ClockStatus.SIGNIN.getStatenum()) {
            now.setClockStatus(ClockStatus.COMPLETE.getStatenum());
            clockMapper.updateById(now);
            return new ResponseMessage("200","成功",true,ClockStatus.COMPLETE.getStatenum());
        }

        return new ResponseMessage("201","失败",false,null);
    }
    @ApiOperation(value = "当日是否打卡")
    @GetMapping("/check")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage check(@RequestParam("employeeId")String eid,@RequestParam("day")String day) throws Exception {
        if(day.length()>10){
            day=day.substring(0,10);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date dayStart = sdf.parse(day+" 00:00:00");
        Date dayEnd = sdf.parse(day+" 23:59:59");
        QueryWrapper<Clock>queryWrapper=new QueryWrapper<>();
        queryWrapper.between("time",dayStart,dayEnd);
        queryWrapper.eq("employee_id",eid);
        Clock tem=clockMapper.selectOne(queryWrapper);
        if((tem!=null)&&(tem.getClockStatus()== ClockStatus.COMPLETE.getStatenum()))
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
