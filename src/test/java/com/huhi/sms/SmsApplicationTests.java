package com.huhi.sms;

import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.entity.Clock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SmsApplicationTests {

    @Autowired
    private ClockMapper clockMapper;

    @Test
    public void testCurd() {
        Clock clock=new Clock();
        clock.setClockStatus(1);
        clock.setEmployeeId("21212");
        clock.setId("4141");
        clock.setTime(new Date());
        clockMapper.insert(clock);
        List<Clock> select = clockMapper.selectList(null);
        System.out.println(select);
    }

}
