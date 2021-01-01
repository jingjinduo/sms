package com.huhi.sms.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@Data
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
