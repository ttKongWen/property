package com.ilovecl.demo.dao;

import com.ilovecl.demo.entity.Maintenance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 维修记录DAO层
 *
 */
@Mapper
public interface MaintenanceDao {

    //    根据ID查询
    Maintenance queryById(int id);

    //    根据报修单ID查询
    Maintenance queryByRepairId(int RepairId);

    //    查询所有维修记录
    List<Maintenance> queryAll();

    //    增加一条维修记录
    int add(Maintenance maintenance);

    int delete(Maintenance maintenance);

}

