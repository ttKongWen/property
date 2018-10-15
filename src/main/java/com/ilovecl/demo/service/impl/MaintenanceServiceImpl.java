package com.ilovecl.demo.service.impl;

import com.ilovecl.demo.dao.MaintenanceDao;
import com.ilovecl.demo.entity.Maintenance;
import com.ilovecl.demo.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维修记录Service层的实现
 *
 */
@Service("maintenaceService")
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    public MaintenanceDao maintenanceDao;

    @Override
    public Maintenance getById(int id) {
        return maintenanceDao.queryById(id);
    }

    /**
     * 获取所有的维修安排
     *
     * @return 所有维修安排
     */
    @Override
    public List<Maintenance> getAll() {
        return maintenanceDao.queryAll();
    }

    /**
     * 取消某条维修安排
     *
     * @param id 对应的维修安排的编号ID
     * @return 是否成功取消
     */
    @Override
    public boolean cancelMaintenance(int id) {
        maintenanceDao.delete(new Maintenance(id));

        return true;
    }

    @Override
    public Maintenance getByRepairId(int repairId) {
        return maintenanceDao.queryByRepairId(repairId);
    }
}
