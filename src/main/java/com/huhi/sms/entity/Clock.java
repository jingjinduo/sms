package com.huhi.sms.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
@ApiModel(value="Clock对象", description="")
@TableName("clock")
public class Clock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty(value = "工号")
    private String employeeId;

    @ApiModelProperty(value = "打卡时间")
    private Date time;

    @ApiModelProperty(value = "打卡类型，0为签到，1为签退")
    private Integer clockStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public Integer getClockStatus() {
        return clockStatus;
    }

    public void setClockStatus(Integer clockStatus) {
        this.clockStatus = clockStatus;
    }

    @Override
    public String toString() {
        return "Clock{" +
            "id=" + id +
            ", employeeId=" + employeeId +
            ", time=" + time +
            ", clockStatus=" + clockStatus +
        "}";
    }
}
