package com.huhi.sms.entity;

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
@ApiModel(value="Manager对象", description="")
@TableName("manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty(value = "登录账号")
    private String loginId;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否高级管理员")
    private Integer isAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "Manager{" +
            "id=" + id +
            ", loginId=" + loginId +
            ", password=" + password +
            ", isAdmin=" + isAdmin +
        "}";
    }
}
