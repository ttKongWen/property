package com.ilovecl.demo.dao;

import com.ilovecl.demo.entity.Technician;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 维修人员DAO层
 *
 */
@Mapper
public interface TechnicianDao {
    //    根据ID查询
    Technician queryById(int id);

    List<Technician> queryAll();

    boolean addTechnician(Technician technician);

    boolean delete(int id);

    boolean update(int id, String name);
}
