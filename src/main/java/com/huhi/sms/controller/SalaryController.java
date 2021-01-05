package com.huhi.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.dao.SalaryMapper;
import com.huhi.sms.entity.Clock;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Salary;
import com.huhi.sms.service.SalaryNService;
import com.huhi.sms.service.impl.SalaryServiceImpl;
import com.huhi.sms.util.DateFormatUtil;
import com.huhi.sms.util.ResponseMessage;
import com.huhi.sms.vo.BetweenVO;
import com.huhi.sms.vo.EmployeeSalary;
import com.huhi.sms.vo.PayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@RequestMapping("/salary")
@Api(tags = "|salary|")
public class SalaryController {

    @Autowired
    SalaryMapper salaryMapper;

    @Autowired
    SalaryNService salaryNService;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    ClockMapper clockMapper;
    //查询工资
    public ResponseMessage historicalSalary(){
        //根据工号查询id
        //根据id查询工资
        return new ResponseMessage();
    }
    @ApiOperation(value = "区间分页查询")
    @PostMapping("/page")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage listAll(@RequestParam("employeeId")String employeeId
    ,@RequestParam("currentPage")int currentPage
            ,@RequestParam("pageSize")int pageSize
    ,@RequestParam("startDate")String startDate
    ,@RequestParam("endDate")String endDate) throws Exception {
        @Data
        class VO{
            List<Salary> list;
            int total;
        };
        VO vo=new VO();

        Date start=DateFormatUtil.string2Date(startDate);
        Date end=DateFormatUtil.string2Date(endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.MONTH, 1);
        end = calendar.getTime();
        if(start.after(end)){
            return new ResponseMessage("201","时间前后冲突",false
                    ,null);
        }
        QueryWrapper<Salary>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("employee_id",employeeId);
        queryWrapper.between("date",start,end);
        Page<Salary> page = new Page<>(currentPage,pageSize);  // 查询第1页，每页返回5条
        IPage<Salary> iPage = salaryMapper.selectPage(page,queryWrapper);
        vo.list=iPage.getRecords();
        vo.total=salaryMapper.selectCount(queryWrapper);
        return new ResponseMessage("200","成功",true
                ,vo);
    }

    @ApiOperation(value = "个人月薪分页查询")
    @PostMapping("/pageEveryOne")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage everyOne(@RequestParam("employeeId")String employeeId
            ,@RequestParam("currentPage")int currentPage
            ,@RequestParam("pageSize")int pageSize) throws Exception {
        System.out.println(employeeId);

        @Data
        class VO{
            List<EmployeeSalary> list;
            Integer total;
        };
        VO vo=new VO();
        QueryWrapper<Employee>queryWrapper=new QueryWrapper<>();
        if(employeeId.length()!=0)
            queryWrapper.eq("employee_id",employeeId);
        Page<Employee> page = new Page<>(currentPage,pageSize);  // 查询第1页，每页返回5条
        IPage<Employee> iPage = employeeMapper.selectPage(page,queryWrapper);
        List<Employee> tem=iPage.getRecords();
        vo.total=employeeMapper.selectCount(queryWrapper);

        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String day=formatter.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date monthStart = sdf.parse(day+"-01 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthStart);
        calendar.add(Calendar.MONTH, 1);
        Date monthEnd = calendar.getTime();
        System.out.println(monthStart);
        System.out.println(monthEnd);
        List<EmployeeSalary> res=new ArrayList<>();
        for(Employee e:tem){
            EmployeeSalary es=new EmployeeSalary();
            es.setEmployeeId(e.getEmployeeId());
            es.setSalary(e.getSalary());
            es.setName(e.getName());
            QueryWrapper<Clock>qw=new QueryWrapper<>();
            qw.eq("employee_id",e.getEmployeeId());
            qw.between("time",monthStart,monthEnd);
            es.setTime(clockMapper.selectCount(qw));
            es.setReal(es.getSalary()
                    .divide(BigDecimal.valueOf(20))
                    .multiply(BigDecimal.valueOf(es.getTime())));
            res.add(es);
        }
        vo.list=res;
        return new ResponseMessage("200","成功",true
                ,vo);
    }

    @ApiOperation(value = "当前工资")
    @PostMapping("/pay")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage pay() throws Exception {

        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String day=formatter.format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date monthStart = sdf.parse(day+"-01 00:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthStart);
        calendar.add(Calendar.MONTH, 1);
        Date monthEnd = calendar.getTime();
        if(salaryMapper.selectCount(new QueryWrapper<Salary>().between(
                "date",monthStart,monthEnd
        ))>0){
            return new ResponseMessage("201","已结算过，无需重复",false
                    ,null);
        }
        List<Employee> tem=employeeMapper.selectList(null);
        List<Salary> res=new ArrayList<>();
        Date now=new Date();
        for(Employee e:tem){
            Salary es=new Salary();
            es.setEmployeeId(e.getEmployeeId());
            es.setSalary(e.getSalary());
            QueryWrapper<Clock>qw=new QueryWrapper<>();
            qw.eq("employee_id",e.getEmployeeId());
            int time=clockMapper.selectCount(qw);
            es.setDate(now);
            es.setSalary(e.getSalary().divide(BigDecimal.valueOf(20)).multiply(
                    BigDecimal.valueOf(time)
            ));
            res.add(es);
        }
        salaryNService.saveBatch(res);
        return new ResponseMessage("200","成功",true
                ,null);
    }


    @ApiOperation(value = "当前工资")
    @PostMapping("/now")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage now(@RequestBody Employee employee) throws Exception {
        System.out.println(employee);
        return new ResponseMessage("200","成功",true
                ,employeeMapper.selectOne(new QueryWrapper<Employee>().eq("employee_id",employee.getEmployeeId()))
                .getSalary());
    }

    @ApiOperation(value = "查询区间")
    @PostMapping("/between")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage between(@RequestBody BetweenVO betweenVO) throws Exception {
        System.out.println(betweenVO.getStartDate());
        Date start=DateFormatUtil.string2Date(betweenVO.getStartDate());
        Date end=DateFormatUtil.string2Date(betweenVO.getEndDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.MONTH, 1);
        end = calendar.getTime();
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
