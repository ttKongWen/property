package com.ilovecl.demo.service.impl;

import com.ilovecl.demo.dao.AdminDao;
import com.ilovecl.demo.entity.Admin;
import com.ilovecl.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 学生Service层的实现
 *
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    @Override
    public Admin getAdminById(int id) {
        return adminDao.queryById(id);
    }

}
