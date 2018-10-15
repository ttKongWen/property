package com.ilovecl.demo.dao;

import com.ilovecl.demo.entity.Repair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 维修单DAO层
 *
 */
@Mapper
public interface RepairDao {
    //    根据ID查询
    Repair queryById(int id);

    List<Repair> queryByStudentId(String studentId);

    //    查询所有报修单
    List<Repair> queryAll();

    //    增加一条报修单记录
    int add(Repair repair);

    //    删除一条报修单记录
    int delete(Repair repair);

    //    更新一条报修单记录
    int update(Repair oldRepair);
}

