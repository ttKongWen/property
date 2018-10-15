package com.ilovecl.demo.dao;

import com.ilovecl.demo.entity.UrgentRepair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 催单DAO层
 *
 */
@Mapper
public interface UrgentRepairDao {
    //    根据ID查询催单记录
    UrgentRepair queryById(int id);

    //    根据报修单ID查询催单记录
    UrgentRepair queryByRepairId(int repairId);

    List<UrgentRepair> queryByStudentId(String studentId);

    //    查询所有催单
    List<UrgentRepair> queryAll();

    //    增加一条催单记录
    int add(UrgentRepair urgentRepair);

    //    删除一条催单记录
    int delete(UrgentRepair urgentRepair);

    //    更新一条催单记录
    int update(UrgentRepair urgentRepair);
}
