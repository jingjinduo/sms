package com.huhi.sms.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EmployeeSalary implements Serializable {
    private static final long serialVersionUID = 1L;
    private String employeeId;
    private String name;
    private BigDecimal salary;
    private Integer time;
    private BigDecimal real;
};
