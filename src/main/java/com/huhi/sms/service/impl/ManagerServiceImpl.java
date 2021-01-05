package com.huhi.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhi.sms.entity.Manager;
import com.huhi.sms.dao.ManagerMapper;
import com.huhi.sms.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2021-01-01
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Autowired(required = false)
    private ManagerMapper managerMapper;

    //按登录名和密码查询管理者
    @Override
    public Manager checkManage(String loginId, String password) {
        return managerMapper.selectOne(new QueryWrapper<Manager>().eq("login_id", loginId).eq("password",password));
    }

    @Override
    public Boolean insertManager(Manager manager) {
        managerMapper.insert(manager);
        return true;
    }

    @Override
    public Manager selectByManaId(String managerLoginId) {
        return managerMapper.selectOne(new QueryWrapper<Manager>().eq("login_id", managerLoginId));
    }

    @Override
    public Page<Manager> selectAllManaByPage(Page<Manager> managerPage) {
        return managerMapper.selectPage(managerPage, null);
    }
}
