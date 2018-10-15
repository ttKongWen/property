package com.ilovecl.demo.service;

import com.ilovecl.demo.entity.Technician;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 维修人员的Service层
 *
 */

@Repository
public interface TechnicianService {

    /**
     * 根据报修单获取维修人员
     * @param technicianId
     * @return
     */
    Technician getById(int technicianId);

    /**
     * 获取所有维修人员
     *
     * @return
     */
    List<Technician> getAllTechnician();

    /**
     * 增加一个维修人员
     *
     * @param name 姓名
     * @return
     */
    boolean addTechnician(String name,String number,String phone);

    /**
     * 删除一个维修人员
     *
     * @param id 维修人员的编号ID
     * @return
     */
    boolean deleteTechnician(int id);

    /**
     * 修改维修人员信息
     *
     * @param id      维修人员编号ID
     * @param newName 姓名
     * @return
     */
    boolean updateTechnician(int id, String newName);

}
