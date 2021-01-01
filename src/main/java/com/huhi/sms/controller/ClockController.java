package com.huhi.sms.controller;


import com.huhi.sms.dao.ClockMapper;
import com.huhi.sms.entity.Clock;
import com.huhi.sms.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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

    @ApiOperation(value = "查询所有")
    @GetMapping("/all")
    //@RequiresPermissions("business:information:delete")
    public ResponseMessage submitApproval() throws Exception {
        return new ResponseMessage("402","成功",true,clockMapper.selectList(null));
    }
}
