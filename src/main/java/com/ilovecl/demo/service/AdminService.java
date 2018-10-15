package com.ilovecl.demo.service;

import com.ilovecl.demo.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * 管理员的Service层
 *
 */

@Repository
public interface AdminService {

    /**
     * 根据id查询学生
     * @param id
     * @return
     */
    Admin getAdminById(int id);
}
