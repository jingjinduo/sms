package com.huhi.sms.entity;

import java.math.BigDecimal;
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
@ApiModel(value="Salary对象", description="")
@TableName("salary")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty(value = "时间")
    private Date date;

    @ApiModelProperty(value = "工资")
    private BigDecimal salary;

    @ApiModelProperty(value = "工号")
    private String employeeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Salary{" +
            "id=" + id +
            ", date=" + date +
            ", salary=" + salary +
            ", employeeId=" + employeeId +
        "}";
    }
}
