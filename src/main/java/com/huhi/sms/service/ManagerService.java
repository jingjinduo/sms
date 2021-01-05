package com.huhi.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.entity.Employee;
import com.huhi.sms.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
public interface ManagerService extends IService<Manager> {

    Manager checkManage(String loginId, String password);

    Boolean insertManager(Manager manager);

    Manager selectByManaId(String managerLoginId);

    Page<Manager> selectAllManaByPage(Page<Manager> managerPage);
}
