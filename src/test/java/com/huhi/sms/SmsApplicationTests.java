package com.huhi.sms;

import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.dao.EmployeeMapper;
import com.huhi.sms.entity.Clock;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.service.EmployeeService;
import io.swagger.models.auth.In;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SmsApplicationTests {

    @Resource
    private ClockMapper clockMapper;
    //创建Mapper对象之后才能使用，相当于传统方式的dao层，也就是持久层，直接和数据库连接，并且操作数据库
    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployeeService employeeService;

    @Test
    public void testCurd() {
        Clock clock=new Clock();
        clock.setClockStatus(1);
        clock.setEmployeeId("123");
        clock.setId("53455");
        clock.setTime(new Date());
        clockMapper.insert(clock);
        List<Clock> select = clockMapper.selectList(null);
        System.out.println(select);
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setLoginId("HUAAN");
        employee.setPassword("123456");
        employee.setEmployeeId("9527");
        employee.setAge(17);
        employee.setName("华安");
        employee.setPhoneNum("13333333333");
        employee.setPicture("C:\\Users\\wang\\Pictures\\picture.jpg");
        employee.setSalary(new BigDecimal(30000));
        employee.setSex(1);
        employeeMapper.insert(employee);
    }


    @Test
    public void testFindByEmployeeId() {

        val employee = employeeService.selectByEmployeeId("9527");
        System.out.println(employee.toString());
    }

}
